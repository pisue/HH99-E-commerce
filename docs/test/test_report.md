# 장애 대응 문서

본 문서는 이커머스 시스템 운영 중 예상치 못한 장애 상황에서 빠르고 체계적으로 대응하기 위한 장애 대응 가이드입니다.

---

## 1. 장애 정의

| **장애 수준** | **설명** |
|-----------|----------------------------------------|
| **Critical** | Redis 또는 DB 전체 장애로 인한 서비스 중단 |
| **Major** | 주문/결제 프로세스 장애, 인기상품/장바구니 조회 불가 |
| **Minor** | API 응답 지연(500ms 이상) 또는 부분적 기능 장애 |

---

## 2. 장애 발생 시 기본 절차

| **단계** | **설명** |
|---------------|-------------------------------------------|
| **장애 감지** | K6 모니터링을 통한 응답시간 및 에러율 감지 |
| **장애 신고** | Slack #장애-alert 채널에 보고 (발생시간, 증상, 영향도) |
| **장애 대응팀 소집** | 개발팀 리더 주도로 장애 대응팀 구성 |
| **장애 조사** | 로그 분석, Redis/DB 상태 확인 |
| **임시 조치** | 캐시 재구성, DB 인덱스 재구축 등 |
| **완전 복구** | 근본 원인 해결 및 서비스 정상화 |
| **장애 보고** | 원인, 조치, 복구시간 포함한 보고서 작성 |

---

## 3. 장애별 대응 프로세스

### 3.1 Redis 캐시 장애

| **항목** | **내용** |
|-----------|-------------------------------------------|
| **증상** | 인기상품 조회 응답시간 3ms → 1,900ms 증가 |
| **점검 항목** | Redis 연결 상태, 메모리 사용량, 캐시 적중률 |
| **조치 방안** | Redis 서버 재시작, 캐시 데이터 재구성 |
| **모니터링 지표** | 캐시 적중률 95% 이상 유지 |

### 3.2 DB 인덱스 성능 저하

| **항목** | **내용** |
|-----------|-------------------------------------------|
| **증상** | 주문조회 23ms → 2s, 장바구니 조회 42ms → 200ms |
| **점검 항목** | DB CPU 사용률, 슬로우 쿼리 로그, 인덱스 상태 |
| **조치 방안** | 인덱스 재구성, 쿼리 최적화 |
| **모니터링 지표** | 쿼리 실행시간 100ms 이하 유지 |

---

## 4. 모니터링 임계치

| **지표** | **주의** | **경고** | **심각** |
|---------|---------|---------|---------|
| API 응답시간 | 200ms | 500ms | 1s |
| 에러율 | 0.1% | 0.5% | 1% |
| CPU 사용률 | 70% | 80% | 90% |
| 메모리 사용률 | 75% | 85% | 95% |

---

## 5. 복구 우선순위

| **순위** | **서비스** | **목표 복구 시간** |
|---------|---------|----------------|
| 1 | 주문/결제 | 30분 이내 |
| 2 | 인기상품/장바구니 | 1시간 이내 |
| 3 | 상품 상세 정보 | 2시간 이내 |

---

## 6. 사후 회고

| **항목** | **내용** |
|---------|-------------------------------------------|
| **개선된 사항** | - 캐싱 도입으로 인기상품 조회 99.8% 성능 개선<br>- 인덱스 최적화로 주문조회 98.8% 성능 개선 |
| **남은 과제** | - 주문 생성 프로세스 최적화 (현재 835ms)<br>- 대규모 트래픽 대비 아키텍처 개선 |
| **향후 계획** | - 캐싱 범위 확대<br>- CQRS 패턴 도입 검토<br>- 비동기 처리 도입 |

---

## 7. 비상 연락망

| **순위** | **담당자** | **연락처** |
|---------|---------|---------|
| 1차 | 당직 개발자 | 010-XXXX-XXXX |
| 2차 | 시스템 운영팀 | 010-XXXX-XXXX |
| 3차 | 개발팀 리더 | 010-XXXX-XXXX |
| 최종 | CTO | 010-XXXX-XXXX |
