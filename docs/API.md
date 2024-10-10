### 1. 잔액 관리 API

### 1.1. 잔액 충전

- **메서드**: `PUT`
- **경로**: `/api/balance/charge`
- **요청 본문**:

    ```json
    
    {
      "userId": 123,
      "amount": 5000
    }
    
    ```

- **응답**:
  - **200 OK**

      ```json
      
      {
        "message": "충전 성공",
        "newBalance": 17000
      }
      
      ```


### 1.2. 잔액 조회

- **메서드**: `GET`
- **경로**: `/api/balance/{userId}`
- **경로 매개변수**:
  - `userId` (integer, 필수): 사용자 ID
- **응답**:
  - **200 OK**

      ```json
      
      {
        "id": 1,
        "userId": 123,
        "amount": 12000
      }
      
      ```


---

### 2. 상품 관리 API

### 2.1. 전체 상품 조회

- **메서드**: `GET`
- **경로**: `/api/products`
- **응답**:
  - **200 OK**

      ```json
      
      [
        {
          "id": 1,
          "name": "스마트폰",
          "description": "최신형 스마트폰",
          "price": 699.99,
          "stock": 50,
          "regDate": "2023-10-11T10:00:00Z"
        },
        {
          "id": 2,
          "name": "노트북",
          "description": "고성능 노트북",
          "price": 999.99,
          "stock": 20,
          "regDate": "2023-10-10T09:00:00Z"
        }
      ]
      
      ```


### 2.2. 특정 상품 조회

- **메서드**: `GET`
- **경로**: `/api/products/{id}`
- **경로 매개변수**:
  - `id` (integer, 필수): 상품 ID
- **응답**:
  - **200 OK**

      ```json
      
      {
        "id": 1,
        "name": "스마트폰",
        "description": "최신형 스마트폰",
        "price": 699.99,
        "stock": 50,
        "regDate": "2023-10-11T10:00:00Z"
      }
      
      ```


### 2.3. 인기 판매 상품 조회

- **메서드**: `GET`
- **경로**: `/api/products/popular`
- **응답**:
  - **200 OK**

      ```json
      
      [
        {
          "id": 1,
          "name": "스마트폰",
          "description": "최신형 스마트폰",
          "price": 699.99,
          "stock": 50,
          "regDate": "2023-10-11T10:00:00Z"
        }
      ]
      
      ```


### 2.4. 상품 등록

- **메서드**: `POST`
- **경로**: `/api/products`
- **요청 본문**:

    ```json
    
    {
      "id": 3,
      "name": "태블릿",
      "description": "고해상도 태블릿",
      "price": 499.99,
      "stock": 30
    }
    
    ```

- **응답**:
  - **200 OK**

      ```json
      
      {
        "id": 3,
        "name": "태블릿",
        "description": "고해상도 태블릿",
        "price": 499.99,
        "stock": 30,
        "regDate": "2023-10-11T11:00:00Z"
      }
      
      ```


---

### 3. 주문 / 결제 API

### 3.1. 주문 요청

- **메서드**: `POST`
- **경로**: `/api/orders`
- **요청 본문**:

    ```json
    
    {
      "userId": 123,
      "createOrderItemRequests": [
        {
          "productId": 1,
          "quantity": 2,
          "itemPrice": 699.99
        },
        {
          "productId": 2,
          "quantity": 1,
          "itemPrice": 999.99
        }
      ],
      "totalPrice": 2099.97
    }
    
    ```

- **응답**:
  - **200 OK**

      ```json
      
      {
        "message": "주문이 완료되었습니다.",
        "orderId": 456
      }
      
      ```


### 3.2. 유저 주문 목록 조회

- **메서드**: `GET`
- **경로**: `/api/orders/{userId}`
- **경로 매개변수**:
  - `userId` (integer, 필수): 사용자 ID
- **응답**:
  - **200 OK**

      ```json
      
      [
        {
          "id": 456,
          "orderDate": "2023-10-11T12:00:00Z",
          "totalPrice": 2099.97
        }
      ]
      
      ```


### 3.3. 주문정보 상세 조회

- **메서드**: `GET`
- **경로**: `/api/orders/{userId}/{orderId}`
- **경로 매개변수**:
  - `userId` (integer, 필수): 사용자 ID
  - `orderId` (integer, 필수): 주문 ID
- **응답**:
  - **200 OK**

      ```json
      
      [
        {
          "id": 1,
          "orderId": 456,
          "productId": 1,
          "quantity": 2,
          "itemPrice": 699.99
        },
        {
          "id": 2,
          "orderId": 456,
          "productId": 2,
          "quantity": 1,
          "itemPrice": 999.99
        }
      ]
      
      ```


---

### 4. 장바구니 API

### 4.1. 장바구니 추가

- **메서드**: `POST`
- **경로**: `/api/carts`
- **요청 본문**:

    ```json
    
    {
      "userId": 123,
      "productId": 1,
      "quantity": 2
    }
    
    ```

- **응답**:
  - **200 OK**

      ```json
      
      {
        "userId": 123,
        "productId": 1,
        "quantity": 2,
        "createdAt": "2023-10-11T10:00:00Z"
      }
      
      ```


### 4.2. 장바구니 목록 조회

- **메서드**: `GET`
- **경로**: `/api/carts/{userId}`
- **경로 매개변수**:
  - `userId` (integer, 필수): 사용자 ID
- **응답**:
  - **200 OK**

      ```json
      
      [
        {
          "userId": 123,
          "productId": 1,
          "quantity": 2,
          "createdAt": "2023-10-11T10:00:00Z"
        }
      ]
      
      ```


### 4.3. 장바구니에서 삭제

- **메서드**: `DELETE`
- **경로**: `/api/carts/{id}`
- **경로 매개변수**:
  - `id` (integer, 필수): 장바구니 ID
- **응답**:
  - **200 OK**

      ```json
      
      {
        "삭제되었습니다": {
          "userId": 1,
          "productId": 1,
          "quantity": 2,
          "createdAt": "2024-10-11T00:47:53.3487708"
        }
      }
      
      ```