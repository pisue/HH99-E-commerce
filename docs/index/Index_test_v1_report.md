# 인기 상품 조회 성능 분석 보고서

## 1. 데이터 현황

### 1.1 전체 데이터 규모
- 총 주문 건수: 4,198,545건
- 최근 3일 완료 주문: 1,778,271건
- 필터링 비율: 42.35%

### 1.2 인덱스 상태
```sql
CREATE INDEX idx_order_item_date_status ON order_item (created_at, order_status);
```
- Cardinality:
- created_at: 61,649
- order_status: 75,608

## 2. 성능 측정 결과

### 2.1 쿼리 실행 시간
- 인덱스 생성 전 실행 시간: 7.32441125초
- 전체 데이터(4,085,911건) 중 5.56% 필터링
- 테이블 풀 스캔 발생

### 2.2 실행 계획 비교

#### 인덱스 생성 전
```sql
type: ALL
rows: 4,085,911
filtered: 5.56%
Extra: Using where;
    Using temporary;
    Using filesort
```

#### 인덱스 생성 후
```sql
type: ALL
rows: 4,085,911
filtered: 25%
Extra: Using where;
    Using temporary;
    Using filesort
```

## 3. 문제점 분석

### 3.1 인덱스 활용 실패
- 생성된 인덱스가 있음에도 옵티마이저가 테이블 풀 스캔 선택
- possible_keys에 인덱스가 표시되지만 실제 사용되지 않음
- filtered 비율이 25%로 증가했으나 여전히 비효율적

### 3.2 성능 저하 요인
1. 대량 데이터 처리
- 전체 데이터의 42.35%를 필터링
- 약 178만 건의 데이터 처리 필요

2. 추가 작업 발생
- Using temporary: 임시 테이블 생성
- Using filesort: 추가 정렬 작업
---
  [3일간의_주문_건수_확인.csv](../src/test/resources/performance/report/3%EC%9D%BC%EA%B0%84%EC%9D%98_%EC%A3%BC%EB%AC%B8_%EA%B1%B4%EC%88%98_%ED%99%95%EC%9D%B8.csv)

[실제_쿼리_실행(인덱스_생성_전).csv](../src/test/resources/performance/report/%EC%8B%A4%EC%A0%9C_%EC%BF%BC%EB%A6%AC_%EC%8B%A4%ED%96%89%28%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%EC%A0%84%29.csv)

[실행시간_확인.csv](../src/test/resources/performance/report/%EC%8B%A4%ED%96%89%EC%8B%9C%EA%B0%84_%ED%99%95%EC%9D%B8.csv)

[옵티마이저가_인덱스를_사용하는지_확인.csv](../src/test/resources/performance/report/%EC%98%B5%ED%8B%B0%EB%A7%88%EC%9D%B4%EC%A0%80%EA%B0%80_%EC%9D%B8%EB%8D%B1%EC%8A%A4%EB%A5%BC_%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94%EC%A7%80_%ED%99%95%EC%9D%B8.csv)

[인덱스_사용_통계.csv](../src/test/resources/performance/report/%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%82%AC%EC%9A%A9_%ED%86%B5%EA%B3%84.csv)

[인덱스_생성_전_실행_계획_확인.csv](../src/test/resources/performance/report/%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%EC%A0%84_%EC%8B%A4%ED%96%89_%EA%B3%84%ED%9A%8D_%ED%99%95%EC%9D%B8.csv)

[인덱스_생성_후_실제_쿼리_실행.csv](../src/test/resources/performance/report/%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%9D%EC%84%B1_%ED%9B%84_%EC%8B%A4%EC%A0%9C_%EC%BF%BC%EB%A6%AC_%EC%8B%A4%ED%96%89.csv)

[인덱스가_실제로_생성되었는지_확인.csv](../src/test/resources/performance/report/%EC%9D%B8%EB%8D%B1%EC%8A%A4%EA%B0%80_%EC%8B%A4%EC%A0%9C%EB%A1%9C_%EC%83%9D%EC%84%B1%EB%90%98%EC%97%88%EB%8A%94%EC%A7%80_%ED%99%95%EC%9D%B8.csv)

[전체_데이터_중_필터링되는_비율_확인.csv](../src/test/resources/performance/report/%EC%A0%84%EC%B2%B4_%EB%8D%B0%EC%9D%B4%ED%84%B0_%EC%A4%91_%ED%95%84%ED%84%B0%EB%A7%81%EB%90%98%EB%8A%94_%EB%B9%84%EC%9C%A8_%ED%99%95%EC%9D%B8.csv)

[테이블과_인덱스_상태_확인.csv](../src/test/resources/performance/report/%ED%85%8C%EC%9D%B4%EB%B8%94%EA%B3%BC_%EC%9D%B8%EB%8D%B1%EC%8A%A4_%EC%83%81%ED%83%9C_%ED%99%95%EC%9D%B8.csv)
