## API 명세

### 1. 잔액 충전 / 조회 API

### 1.1 잔액 충전

- **메서드**: `POST`
- **엔드포인트**: `/api/balance/charge`
- **요청 본문**:

    ```json

    {
      "userId": 0,
      "amount": 0
    }
    
    ```

- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json

    {
      "message": "string",
      "newBalance": 0
    }
    
    ```


### 1.2 잔액 조회

- **메서드**: `GET`
- **엔드포인트**: `/api/balance/{userId}`
- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json

    {
      "id": 0,
      "userId": 0,
      "amount": 0
    }
    
    ```


---

### 2. 상품 조회 API

### 2.1 전체 상품 조회

- **메서드**: `GET`
- **엔드포인트**: `/api/products`
- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json
    [
      {
        "id": 1,
        "name": "상품 A",
        "price": 10000,
        "stock": 50
      },
      {
        "id": 2,
        "name": "상품 B",
        "price": 20000,
        "stock": 30
      }
    ]
    
    ```


### 2.2 특정 상품 조회

- **메서드**: `GET`
- **엔드포인트**: `/api/products/{productId}`
- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json
    {
      "id": "1",
      "name": "상품 A",
      "price": 10000,
      "stock": 50,
      "description": "상품 A에 대한 설명"
    }
    
    ```

### 2.3 상품 등록

- **메서드**: `POST`
- **엔드포인트**: `/api/products`
- **요청 본문**:


  ```json
  {
    "id": 1,
    "name": "상품 A",
    "price": 10000,
    "stock": 50,
    "description": "상품 A에 대한 설명"
  }
  
  ```


---

### 3. 주문 / 결제 API

- **메서드**: `POST`
- **엔드포인트**: `/api/orders`
- **요청 본문**:

    ```json
    {
      "userId": "string",
      "orderDetails": [
        {
          "productId": "1",
          "quantity": 2
        },
        {
          "productId": "2",
          "quantity": 1
        }
      ]
    }
    
    ```

- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json
    {
      "message": "주문 성공",
      "orderId": "12345"
    }
    
    ```


---

### 4. 인기 판매 상품 조회 API

- **메서드**: `GET`
- **엔드포인트**: `/api/products/popular`
- **응답**:
    - **상태 코드**: `200 OK`
    - **응답 본문**:

    ```json
    [
      {
        "id": "1",
        "name": "상품 A",
        "sales": 150
      },
      {
        "id": "2",
        "name": "상품 B",
        "sales": 100
      }
    ]
    
    ```