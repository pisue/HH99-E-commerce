# 카프카 연동 및 테스트

---
## 설치 및 실행
[docker-compose.yml](../../docker-compose.yml)
- kafka
- zookeeper
- kafka-ui

[application.yml](../../src/main/resources/application.yml)
- consumer, producer 설정
---

### Kafka 연결 로그 확인
![initialiazing-kafka.png](initialiazing-kafka.png)

### Kafka-ui-main
![kafka-ui-main.png](kafka-ui-main.png)

### HTTP-Client-test
![HTTP-Client-Test.png](HTTP-Client-Test.png)

### Topic(order-create)
![topic-order-create.png](topic-order-create.png)

### Topic-message
![topic-message.png](topic-message.png)
![topic-message-detail.png](topic-message-detail.png)

### Consumer
![consumer-group-01.png](consumer-group-01.png)
![consumer-log.png](consumer-log.png)

### Outbox-data
![outbox-data.png](outbox-data.png)

### Platform-api 메시지 출력
![platform-api.png](platform-api.png)

### Scheduled 테스트
![Scheduled.png](Scheduled.png)
- 그전에 스케줄 로그가 있었는데.. 그거 캡쳐가 안된것같습니다 ;ㅅ;...




