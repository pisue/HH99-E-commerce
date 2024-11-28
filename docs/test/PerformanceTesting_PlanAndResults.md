# 🗓 장애 대응 및 성능 테스트 문서

---

## 목차
* **[STEP: 19]** 부하 테스트 계획 및 수행
    + 부하 테스트의 목적
    + 테스트 대상 및 시나리오
    + 계획 및 스크립트 작성
* 첨부 자료 (개선 전)
* **[STEP: 20]** 성능 지표 분석 및 개선
    + 테스트 결과 요약
    + 장애 대응 방안
    + 배포 스펙 고려 사항
* 첨부 자료 (개선 후)
* 결론

---

## 부하 테스트 계획 및 수행
### 1. 부하 테스트의 목적
- **주요 목적**: 애플리케이션의 안정성 및 병목 구간 탐지
- **추가 목표**: 장애 대응 체계 마련 및 적절한 배포 스펙 도출

### 2. 테스트 대상 및 시나리오
- **테스트 대상**:
    - 주요 API 
      - 주요 API를 대상으로 시나리오를 통해 테스트 진행 
      ```
        1_인기상품_조회
        2_상품_정보_상세_조회
        3_장바구니_추가
        4_장바구니_조회
        5_포인트_충전
        6_주문요청
        7_주문정보_조회
      ```
    - 데이터베이스 및 외부 연동 서비스
- **테스트 시나리오**:
    1. 정상적인 트래픽 수준에서의 응답 속도와 처리량 측정
    2. 높은 트래픽 상황에서의 시스템 한계 측정
    3. 예외 상황(서비스 다운, 네트워크 단절 등)에 대한 대응 확인

### 3. 계획 및 스크립트 작성
- **사용 도구**: 
> philhawthorne 컨테이너 사용 : K6 + Grafana + InfluxDB [관련링크](https://hub.docker.com/r/philhawthorne/docker-influxdb-grafana)
```
docker run -d \
  --name docker-influxdb-grafana \
  -p 3003:3003 \
  -p 3004:8083 \
  -p 8086:8086 \
  -v /pisue/Document/influxdb:/var/lib/influxdb \
  -v /pisue/Document/grafana:/var/lib/grafana \
  philhawthorne/docker-influxdb-grafana:latest
```
- **Mapped Port**
```
Host		Container		Service

3003		3003			grafana
3004		8083			chronograf
8086		8086			influxdb
```


- **스크립트 구성**:
    - 각 시나리오에 맞는 부하 생성 로직
    - 응답 시간, 처리량, 에러율 등의 주요 성능 지표 측정
> [K6 테스트 시나리오 스크립트.js](../../src/test/resources/k6/create-order-k6.js)

## 📄 첨부 자료
- 테스트 시나리오 및 스크립트: [K6 테스트 시나리오 스크립트.js](../../src/test/resources/k6/create-order-k6.js)
- 성능 지표 분석 결과 및 장애 대응 방안: [1차 성능 테스트 보고서](test_result_1.md)

---

## ✅ 결론

