# 인기 상품 조회 성능 분석 보고서 V2

## 1. 테스트 환경 및 데이터 현황

### 1.1 데이터 규모
- 전체 주문 레코드: 4,198,545건
- 최근 3일 완료 주문: 1,788,415건
- 필터링 비율: 42.59%

### 1.2 인덱스 상태 (idx_order_item_optimal)
- created_at: Cardinality 58,802
- order_status: Cardinality 63,933
- product_id: Cardinality 2,370,267

## 2. 성능 측정 결과

### 2.1 쿼리 실행 시간
- 주요 쿼리 실행 시간: 1.73초
    - 이전 버전(7.32초) 대비 76.3% 성능 향상
    - 실시간 서비스에는 여전히 개선 필요

### 2.2 인덱스 사용 통계
- Handler_read_key: 3,576,976 (인덱스를 통한 읽기)
- Handler_read_next: 5,987,120 (인덱스 순차 스캔)
- Handler_read_rnd_next: 8,397,877 (테이블 순차 스캔)

### 2.3 실행 계획 분석
인덱스 생성 후:
```sql
PRIMARY: <derived2> (rows: 5)
PRIMARY: p (type: eq_ref, rows: 1)
DERIVED: order_item (type: ALL, rows: 4,085,911, filtered: 25%)
```

## 3. 성능 분석

### 3.1 개선된 부분
1. 실행 시간 단축
    - 7.32초 → 1.73초
    - 76.3% 성능 향상

2. 인덱스 활용도
    - Handler_read_key 비율 증가
    - 인덱스를 통한 데이터 접근 개선

### 3.2 남은 문제점
1. 여전히 테이블 풀 스캔 발생
    - `type: ALL` in EXPLAIN
    - `Using temporary; Using filesort` 지속

2. 높은 필터링 비율
    - 전체 데이터의 42.59% 처리 필요
    - 메모리 사용량 여전히 높음

## 4. 추가 개선 방안

### 4.1 단기 개선안
1. 쿼리 튜닝
    - 서브쿼리 최적화
    - 임시 테이블 사용 최소화

2. 실행 계획 개선
    - ORDER BY 절 최적화
    - GROUP BY 처리 방식 개선

### 4.2 중장기 개선안
1. 데이터 파티셔닝
    - 날짜 기반 파티셔닝 도입
    - 불필요한 데이터 스캔 방지

2. 캐싱 전략
    - 인기 상품 결과 캐싱
    - 주기적 갱신 방식 도입

## 5. 결론
- 성능이 76.3% 개선되었으나 여전히 최적화 필요
- 실시간 서비스를 위해서는 1초 이내 응답 목표
- 캐싱 전략과 함께 추가적인 쿼리 최적화 필요

---
[v2_3일간의_주문_건수_확인.csv](../../src/test/resources/performance/resport_v2/v2_3%EC%9D%BC%EA%B0%84%EC%9D%98_%EC%A3%BC%EB%AC%B8_%EA%B1%B4%EC%88%98_%ED%99%95%EC%9D%B8.csv)

[v2_옵티마이저가_인덱스를_사용하는지_확인.csv](../../src/test/resources/performance/resport_v2/v2_%E1%84%8B%E1%85%A9%E1%86%B8%E1%84%90%E1%85%B5%E1%84%86%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%80%E1%85%A1_%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%83%E1%85%A6%E1%86%A8%E1%84%89%E1%85%B3%E1%84%85%E1%85%B3%E1%86%AF_%E1%84%89%E1%85%A1%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB%E1%84%8C%E1%85%B5_%E1%84%92%E1%85%AA%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AB.csv)

[v2_테이블과_인덱스_상태_확인.csv](../../src/test/resources/performance/resport_v2/v2_%E1%84%90%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%B3%E1%86%AF%E1%84%80%E1%85%AA_%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%83%E1%85%A6%E1%86%A8%E1%84%89%E1%85%B3_%E1%84%89%E1%85%A1%E1%86%BC%E1%84%90%E1%85%A2_%E1%84%92%E1%85%AA%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AB.csv)

[v2_실제_쿼리_실행(인덱스_생성_전).csv](../../src/test/resources/performance/resport_v2/v2_%EC%8B%A4%EC%A0%9C_%EC%BF%BC%EB%A6%AC_%EC%8B%A4%ED%96%89%28%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%EC%A0%84%29.csv)

[v2_실행_시간_확인.csv](../../src/test/resources/performance/resport_v2/v2_%EC%8B%A4%ED%96%89_%EC%8B%9C%EA%B0%84_%ED%99%95%EC%9D%B8.csv)

[v2_인덱스_사용_통계(기본_정보).csv](../../src/test/resources/performance/resport_v2/v2_%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%82%AC%EC%9A%A9_%ED%86%B5%EA%B3%84%28%EA%B8%B0%EB%B3%B8_%EC%A0%95%EB%B3%B4%29.csv)

[v2_인덱스_사용률_확인.csv](../../src/test/resources/performance/resport_v2/v2_%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%82%AC%EC%9A%A9%EB%A5%A0_%ED%99%95%EC%9D%B8.csv)

[v2_인덱스_생성_전_실행_계획_확인.csv](../../src/test/resources/performance/resport_v2/v2_%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%EC%A0%84_%EC%8B%A4%ED%96%89_%EA%B3%84%ED%9A%8D_%ED%99%95%EC%9D%B8.csv)

[v2_인덱스_생성_후_실제_쿼리_실행.csv](../../src/test/resources/performance/resport_v2/v2_%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%ED%9B%84_%EC%8B%A4%EC%A0%9C_%EC%BF%BC%EB%A6%AC_%EC%8B%A4%ED%96%89.csv)

[v2_인덱스가_실제로_생성되었는지_확인.csv](../../src/test/resources/performance/resport_v2/v2_%EC%9D%B8%EB%8D%B1%EC%8A%A4%EA%B0%80_%EC%8B%A4%EC%A0%9C%EB%A1%9C_%EC%83%9D%EC%84%B1%EB%90%98%EC%97%88%EB%8A%94%EC%A7%80_%ED%99%95%EC%9D%B8.csv)

[v2_전체_데이터_중_필터링되는_비율_확인(3일_기준).csv](../../src/test/resources/performance/resport_v2/v2_%EC%A0%84%EC%B2%B4_%EB%8D%B0%EC%9D%B4%ED%84%B0_%EC%A4%91_%ED%95%84%ED%84%B0%EB%A7%81%EB%90%98%EB%8A%94_%EB%B9%84%EC%9C%A8_%ED%99%95%EC%9D%B8%283%EC%9D%BC_%EA%B8%B0%EC%A4%80%29.csv)
