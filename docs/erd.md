## e 커머스 ERD


---

```mermaid
erDiagram

    %% 사용자 테이블
    user {
        integer id PK "사용자 ID"
        varchar username "사용자 이름"
        varchar email "이메일"
        varchar password "비밀번호"
        timestamp created_at "생성 일자"
    }

    %% 잔액 테이블
    balance {
        integer id PK "잔액 ID"
        integer user_id FK "사용자 ID"
        decimal amount "잔액 금액"
    }

    %% 상품 테이블
    product {
        integer id PK "상품 ID"
        varchar name "상품 이름"
        text description "상품 설명"
        decimal price "상품 가격"
        integer stock "재고 수량"
        timestamp reg_date "등록 일자"
    }

    %% 주문 테이블
    order {
        integer id PK "주문 ID"
        integer user_id FK "사용자 ID"
        timestamp order_date "주문 일자"
        decimal total_price "총 주문 금액"
    }

    %% 주문 상세 테이블
    order_item {
        integer id PK "주문 상세 ID"
        integer order_id FK "주문 ID"
        integer product_id FK "상품 ID"
        integer quantity "주문 수량"
        decimal item_price "상품 가격"
    }

    %% 장바구니 테이블
    cart {
        integer id PK "장바구니 ID"
        integer user_id FK "사용자 ID"
        integer product_id FK "상품 ID"
        integer quantity "장바구니 수량"
        timestamp created_at "생성 일자"
    }

    %% 관계 정의
    user ||--o{ balance : "사용자 - 잔액 관계"
    user ||--o{ order : "사용자 - 주문 관계"
    user ||--o{ cart : "사용자 - 장바구니 관계"
    order ||--o{ order_item : "주문 - 주문 상세 관계"
    product ||--o{ order_item : "상품 - 주문 상세 관계"
    product ||--o{ cart : "상품 - 장바구니 관계"

```

---

### 테이블 설명

1. **사용자 (user)**:
    - **속성**: 사용자 ID, 이름, 이메일, 비밀번호, 생성 일자
    - **설명**: 시스템의 사용자 정보를 저장합니다.
2. **잔액 (balance)**:
    - **속성**: 잔액 ID, 사용자 ID, 잔액 금액
    - **설명**: 각 사용자의 잔액 정보를 관리합니다.
3. **상품 (product)**:
    - **속성**: 상품 ID, 이름, 설명, 가격, 재고 수량, 등록 일자
    - **설명**: 판매하는 상품의 정보를 저장합니다.
4. **주문 (order)**:
    - **속성**: 주문 ID, 사용자 ID, 주문 일자, 총 주문 금액
    - **설명**: 사용자가 생성한 주문 정보를 기록합니다.
5. **주문 상세 (order_item)**:
    - **속성**: 주문 상세 ID, 주문 ID, 상품 ID, 주문 수량, 상품 가격
    - **설명**: 특정 주문에 포함된 상품과 그 수량을 기록합니다. 다.
6. **장바구니 (cart)**:
    - **속성**: 장바구니 ID, 사용자 ID, 상품 ID, 장바구니 수량, 생성 일자
    - **설명**: 사용자가 선택한 상품을 임시로 저장하는 기능을 제공합니다.

---
