# 🗂️ 서비스 분리 및 트랜잭션 처리 방안 설계

---

## 목차

* [개요]()
* [서비스 분리 설계 및 트랜잭션 처리 방안]()
  + [서비스 분리 및 주요 담당 업무]()
  + [상품 주문 - 서비스 분리 설계]()
* [SAGA 패턴]()
    + [동작 방식]()
    + [코레오그래피 기반 사가 (Choreography-based Saga)]()
    + [오케스트레이션 기반 사가 (Orchestration-based Saga)]()
* [구현현황]()
* [참고]()

---


## 개요
> 분산 시스템에서 데이터 일관성을 유지하기 위한 트랜잭션 관리 방법에 대한 고민

## 서비스 분리 설계 및 트랜잭션 처리 방안

---

### 서비스 분리 및 주요 담당 업무
- `주문 서비스` : 주문의 상태를 관리하고 주문정보를 저장
- `상품 서비스` : 상품의 정보를 조회하고 주문이 진행되면 재고를 차람
- `포인트 서비스` : 유저의 포인트를 관리하고 주문이 진행되면 포인트를 차감
- `외부 플랫폼 서비스` : 알람, 메일 등을 처리함

# Kafka 기반 사가 패턴: 코레오그라피 접근 방식
이 문서는 Kafka를 활용한 사가 패턴에서 코레오그라피 접근 방식을 사용하여 서비스 간 이벤트 흐름을 설명합니다. 이 흐름은 전자상거래 주문 처리 과정에 초점을 맞추고 있습니다.

## 이벤트 흐름

1. **주문 서비스 (Order Service)**:
  - Kafka 토픽 `order-create`에 `order-create` 이벤트를 발행합니다.
  - 이벤트는 사용자 ID, 주문 ID, 주문 항목 등의 정보를 포함합니다.
2. **재고 서비스 (Stock Service)**:
  - Kafka 토픽 `order-create`에서 `order-create` 이벤트를 수신합니다.
  - 이벤트를 처리하여 주문된 상품의 재고를 차감합니다.
  - Kafka 토픽 `stock-decrease`에 `stock-decrease` 이벤트를 발행합니다.
  - 이벤트는 상품 ID와 갱신된 재고 정보를 포함합니다.
3. **잔액 서비스 (Balance Service)**:
  - Kafka 토픽 `stock-decrease`에서 `stock-decrease` 이벤트를 수신합니다.
  - 이벤트를 처리하여 사용자의 잔액에서 총 주문 금액을 차감합니다.
  - Kafka 토픽 `pay-complete`에 `pay-complete` 이벤트를 발행합니다.
  - 이벤트는 주문의 최종 상태와 잔액 갱신 정보를 포함합니다.
4. **주문 서비스 (Order Service)**:
  - Kafka 토픽 `order-complete`에서 `order-complete` 이벤트를 수신합니다.
  - 데이터베이스에서 주문 상태를 `complete`로 업데이트합니다.

## SAGA 패턴
- Saga Pattern은 마이크로 서비스에서 데이터 일관성을 관리하는 방법.
- 각 서비스는 로컬 트랜잭션을 가지고 있으며, 해당 서비스 데이터를 업데이트하며 메시지 또는 이벤트를 발행해서, 다음단계 트랜잭션을 호출하게 된다.
- 만약, 해당 프로세스가 실패하게 되면 데이터 정합성을 맞추기 위해 트랜잭션에 대해 보상 트랜잭션을 실행한다.
> 각기 다른 분산 서버에서 다른 DB 벤더사를 사용하고 있어도, Saga Pattern을 사용하면 데이터 일관성을 보장받을 수 있다. 또한 트랜잭션 실패시, 보상 트랜잭션으로 데이터 정합성을 맞출 수 있다.

### 코레오그래피 기반 사가 (Choreography-based Saga)
- 서비스 끼리 직접적으로 통신하지 않고, 이벤트 Pub/Sub을 활용해서 통신하는 방식
- 프로세스를 진행하다가 여러 서비스를 거쳐 서비스(Stock, Payment)에서 실패(예외처리 혹은 장애)가 난다면 보상 트랜잭션 이벤트를 발생
- 장점 : 간단한 workflow에 적합하며 추가 서비스 구현 및 유지관리가 필요하지 않아 간단하게 세팅가능
- 단점 : 트랜잭션을 시뮬레이션하기 위해 모든 서비스를 실행해야하기 때문에 통합테스트와 디버깅이 어렵다

### 오케스트레이션 기반 사가 (Orchestration-based Saga)
- **중안 조장자(Orchestrator)** 가 모든 SAGA의 단계를 관리하며, 이벤트를 발행하고 각 서비스가 이를 처리하도록 요청
- 중앙 조정자는 각 단계의 성공/실패 상태를 추적하고, 실패 시 롤백 작업을 지시
- 트랜잭션 조정자는 각 서비스의 작업을 순서대로 호출하고, 오류가 발생할 경우 보상 트랜잭션을 수행하도록 지시



## 🚀 구현 현황 (Orchestration 기반 Saga 시뮬레이션)

현재 프로젝트는 **단일 DB(Monolith)** 환경이지만, 추후 MSA 전환을 대비하여 **OrderFacade**가 오케스트레이터 역할을 수행하는 형태의 트랜잭션 관리를 시뮬레이션하고 있습니다.

### 구현 상세
*   **Orchestrator:** `OrderFacade`
*   **트랜잭션 관리:** `@Transactional`을 사용하지 않고, 각 단계별 서비스(`ProductService`, `BalanceService`)가 독립적인 트랜잭션을 가집니다.
*   **보상 트랜잭션 (Compensation Transaction):**
    *   프로세스 진행 중 예외 발생 시 `try-catch` 블록에서 이미 성공한 이전 단계들의 작업을 역순으로 취소합니다.

### 트랜잭션 흐름 및 보상 로직
1.  **재고 차감 (`ProductService.deductProductStock`)**
    *   성공 시: `deductedStocks` 리스트에 차감 정보 기록.
    *   실패 시: 전체 프로세스 종료 (보상 불필요).
2.  **잔액 차감 (`BalanceService.deduct`)**
    *   성공 시: `isBalanceDeducted` 플래그를 `true`로 설정.
    *   실패 시:
        *   **보상:** `deductedStocks`에 기록된 상품들의 재고를 `ProductService.compensateStock`을 통해 복구.
3.  **주문 생성 및 이벤트 발행 (`OrderService`, `OrderEventService`)**
    *   성공 시: 전체 프로세스 완료.
    *   실패 시:
        *   **보상 1:** 잔액 복구 (`BalanceService.compensate`).
        *   **보상 2:** 재고 복구 (`ProductService.compensateStock`).

### 코드 예시 (`OrderFacade.java`)
```java
try {
    // 1. 재고 차감
    productService.deductProductStock(...);
    
    // 2. 잔액 차감
    balanceService.deduct(...);

    // 3. 주문 생성
    orderService.createOrder(...);
    
} catch (Exception e) {
    // 실패 시 보상 트랜잭션 실행
    productService.compensateStock(...); // 재고 복구
    if (balanceIsDeducted) {
        balanceService.compensate(...); // 잔액 복구
    }
    throw e;
}
```
이러한 구조를 통해 서비스 간 결합도를 낮추고, 분산 환경에서의 데이터 정합성을 유지하는 방법을 학습 및 적용하였습니다.



- [블로그](https://velog.io/@hgs-study/saga-1) 글을 참고하여 Kafka 설치


## 카프카 패키지 구조
```
- controller
- consumer
    - kafka
        - consumer
            - KafkaMessageConsumer
            - Xxx(KafkaMessage)Consumer
- application
    - service
- domain
- infra
    - kafka
        - producer
            - KafkaMessageProducer.send(Topic<T> topic, T message)
```

## 참고
- [Saga 패턴을 이용한 분산 트랜잭션 제어(결제 프로세스 실습)](https://velog.io/@hgs-study/saga-1)
- [[NHN FORWARD 22] 분산 시스템에서 데이터를 전달하는 효율적인 방법](https://www.youtube.com/watch?v=uk5fRLUsBfk&t=1416s)

---
