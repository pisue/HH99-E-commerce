## 🗓️ 인덱스 분석 보고서 
- DB Query Optimization (feat. Index) 

---
### 인덱스(.. 회사에서 사용해봤지만 정말 잘 알고 사용했나?)
> 중요 🚨 : 인덱스 대상 컬럼의 핵심은 높은 카디널리티(Cardinality) 즉, 데이터의 중복 수치를 잘 고려해야 한다.
- 나의 하찮고 부족한 멘토링 질문, 그러나 큰걸 얻었다.!
```sql
SELECT p FROM Product p
JOIN OrderItem oi ON p.id = oi.productId
WHERE oi.createDateTime BETWEEN :startDateTime AND :endDateTime
GROUP BY p.productId
ORDER BY SUM(oi.quantity) DESC
LIMIT :topNumber

인기 상품 조회에서 OrderItem의 productId로 JOIN을 하고있는데
이런 경우 인덱스를 어떻게 구성해야 JOIN 성능이 향상될 수 있을 까요?
이런경우는 OrderItem에 인덱스 구성을 고려해보는게 좋을까요?

CREATE INDEX idx_order_item_product_date_quantity ON OrderItem (productId, createDateTime, quantity);
여기서 quantity를 할 필요가 있을지 고민이됩니다.
 => 이 친구의 인덱스가 의미가 있으려면 이런 요구사항이면 좋겠어요.
	 최근 일년 중 이 상품이 구매된 최근 50개 주문의 유저.. 등등 정보들을 조합니다.

createdDataTime 정도의 인덱스가 현실적으로 가능해 보이기는 한다.
조인시에는 몇개의 테이블이 있던 "하나의 테이블에서만 인덱스를 활용"할수 있기에 적절한 하나의 인덱스를 잘 만들어 놓고
조인할 테이블은 조인 키 기반으로 데이터를 fetch 해오기만 합니다.
```
✨ 항상 인덱스를 쓰는 목적, 요구사항을 잘 분석하는게 우선!!

✨ 그리고 카디널리티의 순서!!

### 내가 선택한 시나리오에서 어디에 Index를 사용해볼 수 있을까?
> 시나리오 : E-Commerce
- 문제점 : 지난 과제가 밀려있어 Repository에서 Query를 조회하는게 인기상품 조회밖에 없다...
- 아직 캐싱도 진행하지 못하였다
- 포기하지말고 인기상품 조회에서 Index를 활용해보자!! (돈깁업..)
---
(이제 보고서 진짜 시작)
## 1. 배경

### 1.1 인덱스 도입 배경
이커머스 서비스에서 인덱스는 어디에 적용할 수 있을까?
```
- 메인 페이지의 인기 상품 섹션
- 카테고리별 인기 상품 추천
- 실시간 트렌드 분석
```
>여기서 내가 할수있는건... 카테코리별 인기 상품 추천 (대신, 카테고리를 제외한..)

### 1.2 기존 문제점
- 주문 데이터(order_item)는 지속적으로 증가하는 특성
- 전체 주문 중 최근 3일 데이터만 필요
- 완료된 주문(COMPLETED)만 필터링 필요
- 테이블 Full Scan으로 인한 성능 저하

## 2. 인덱스 설계

### 2.1 인덱스 선정 기준
```sql
CREATE INDEX idx_order_item_date_status
ON order_item (created_at, order_status);
```

다음과 같은 이유로 복합 인덱스를 선택했습니다:
1. `created_at`: 최근 3일 데이터 필터링 (높은 선택성)
2. `order_status`: 'COMPLETED' 상태 필터링 (추가 필터링)

### 2.2 설계 고려사항
- 날짜 범위가 먼저 오는 것이 효율적
    - 최근 3일 데이터로 대부분 필터링됨
    - 남은 데이터에서 상태 필터링
- product_id는 인덱스에서 제외
    - GROUP BY에만 사용되므로 필터링 후 처리

## 3. 성능 테스트 결과 분석
> [성능테스트를 위한 sql (실패작)](../../src/test/resources/performance/popular-products-performance-test.sql)

---
### 3.1 예상되는 개선 효과
1. 데이터 접근 방식
    - Before: 전체 테이블 스캔
    - After: 인덱스를 통한 필터링

2. 처리할 데이터량
    -  최근 3일 + COMPLETED 상태 데이터만

3. 메모리 사용
    - 필요한 데이터만 먼저 필터링하여 메모리 사용 효율화

### 3.2 실제 테스트 결과 분석 (🚨🚨대실패🚨🚨)
> [Index_test_v1_report.md](Index_test_v1_report)

---
## 4. 개선 방안

### 4.1 인덱스 재설계
```sql
CREATE INDEX idx_order_item_optimal ON order_item
(created_at, order_status, product_id);
```

### 인덱스 설계 근거
1. `created_at`: 날짜 범위 검색의 선두 컬럼 (높은 선택성)
2. `order_status`: 'COMPLETED' 필터링 (추가 필터링)
3. `product_id`: GROUP BY 절 최적화

## 5. V2 성능 테스트 결과 분석
> [V2_성능테스트를 위한 sql](../../src/test/resources/performance/popular-products-performance-test-v2.sql)

### 5.1 개선된 부분
1. 실행 시간 단축
    - 7.32초 → 1.73초
    - 76.3% 성능 향상

2. 인덱스 활용도
    - Handler_read_key 비율 증가
    - 인덱스를 통한 데이터 접근 개선

### 5.2 남은 문제점
1. 여전히 테이블 풀 스캔 발생
    - `type: ALL` in EXPLAIN
    - `Using temporary; Using filesort` 지속

2. 높은 필터링 비율
    - 전체 데이터의 42.59% 처리 필요
    - 메모리 사용량 여전히 높음 

### 5.3 상세 분석
> [Index_test_v2_report.md](Index_test_v2_report)



## 마무리
```
이정도로 만족해야하는지...?
여전히 풀 스캔이 발생하는 점은 고민해봐야 할 문제..
그리고 밀린과제중 캐싱을 꼭 해야함을 느낄수있었던..
``` 
