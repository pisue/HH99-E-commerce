import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    create_order_scenario: {
      exec: 'create_order',
      executor: 'per-vu-iterations',
      vus: 5,
      iterations: 10,
      maxDuration: '10000m',
    }
  }
};

const scenarioTagName = Object.freeze({
  1: "1_인기상품_조회",
  2: "2_상품_정보_상세_조회",
  3: "3_장바구니_추가",
  4: "4_장바구니_조회",
  5: "5_포인트_충전",
  6: "6_주문요청",
  7: "7_주문정보_조회"
});

const host = 'http://localhost:8080';

export function create_order() {
  const userId = Math.floor(Math.random() * 1050) + 1
  const defaultParam = {
    headers: {
      'Content-Type' : 'application/json',
      'user-id' : userId
    }
  }
  console.log("------- START -------")
  // Scenario_1: 인기 상품 조회
  const popularProductJson = getPopularProducts(defaultParam, 1);
  if(!popularProductJson) return
  const productId = popularProductJson.map(item => item.productResponse.id);

  const randomProductId = productId[Math.floor(Math.random() * 5)]

  // Scenario_2: 상품 정보 상세 조회
  const productInfo = getProduct(defaultParam, 2, randomProductId);


  // Scenario_3: 장바구니 추가
  addCart(defaultParam, 3, productInfo);

  // Scenario_4: 장바구니 조회
  const cartInfos = getCarts(defaultParam, 4);

  // Scenario_5: 포인트 충전
  chargePoint(defaultParam, 5, cartInfos);

  // Scenario_6: 주문 요청
  const orderId = createOrder(defaultParam, 6, cartInfos);

  // Scenario_7: 주문정보 조회
  getOrderInfo(defaultParam, 7, orderId);

}

// scenario_1
function getPopularProducts(defaultParam, scenarioNum) {
  const params = getHeadersWithTags(defaultParam, scenarioNum);

  const res = http.get(
      `${host}/api/products/popular?topNumber=5&lastDays=3`,
      params
  )
  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`1_인기상품_조회: ${res.body}`);
  sleep(1);
  return JSON.parse(res.body);
}

// scenario_2
function getProduct(defaultParam, scenarioNum, id) {
  const params = getHeadersWithTags(defaultParam, scenarioNum);

  const res = http.get(
      `${host}/api/products/${id}`,
      params
  )
  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`2_상품정보_상세_조회: ${res.body}`)
  sleep(1);
  return JSON.parse(res.body);
}

// scenario_3
function addCart(defaultParam, scenarioNum, productInfo){
  const params = getHeadersWithTags(defaultParam, scenarioNum);
  const productId = productInfo.id;
  const quantity = Math.floor(Math.random() * 4 + 1);
  const cartRequest = {
        userId: defaultParam.headers['user-id'],
        productId: productId,
        quantity: quantity
      };

  const res = http.post(
      `${host}/api/carts`,
      JSON.stringify(cartRequest),
      params
  )
  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`3_장바구니_추가: ${res.status}`)
}

// scenario_4
function getCarts(defaultParam, scenarioNum) {
  const params = getHeadersWithTags(defaultParam, scenarioNum);
  const userId = defaultParam.headers['user-id'];
  const res = http.get(
      `${host}/api/carts/${userId}`,
      params
  )
  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`4_장바구니_조회: ${res.body}`);
  return JSON.parse(res.body);
}

// scenario_5
function chargePoint(defaultParam, scenarioNum, cartInfos) {
  const params = getHeadersWithTags(defaultParam, scenarioNum);
  const totalPrice = cartInfos.reduce((sum, item) => {
    const productTotal = item.productPrice * item.quantity;
    return sum + productTotal;
  }, 0);
  const chargeBalanceRequest = {
    userId : defaultParam.headers['user-id'],
    amount : totalPrice
  }
  const res = http.patch(
      `${host}/api/balance/charge`,
      JSON.stringify(chargeBalanceRequest),
      params
  );

  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`5_포인트_충전: ${res.status}`);
}
// scenario_6

function createOrder(defaultParam, scenarioNum, cartInfos){
  const params = getHeadersWithTags(defaultParam, scenarioNum);
  const createOrderRequest = getCreateOrderRequest(cartInfos);
  const res = http.post(
      `${host}/api/orders`,
      JSON.stringify(createOrderRequest),
      params
  )

  check(res, { 'status was 200': (r) => r.status === 200 });
  const resBody = JSON.parse(res.body);
  const orderId = resBody.id;
  console.log(`6_주문생성: ${orderId}`);
  return orderId;
}
// scenario_7
function getOrderInfo(defaultParam, scenarioNum, id) {

  const params = getHeadersWithTags(defaultParam, scenarioNum)
  const res = http.get(
      `${host}/api/orders/order/${id}`,
      params
  )
  check(res, { 'status was 200': (r) => r.status === 200 });
  console.log(`7_주문정보_조회: ${res.body}`);
}

// 헤더정보
function getHeadersWithTags(defaultParam, scenarioNum) {
  return {
    headers: defaultParam.headers,
    tags: {
      name: scenarioTagName[scenarioNum]
    }
  }
}

// 주문 정보 생성
function getCreateOrderRequest(cartInfos) {

  // CreateOrderRequest 생성
  const orderCreateRequests = cartInfos.map(cart => {
    return {
      productId: cart.productId,
      quantity: cart.quantity
    };
  });

  // CreateOrderRequest 객체 리턴
  return {
    userId: cartInfos[0].userId, // 모든 CartResponse의 userId가 동일하다고 가정
    orderCreateRequests: orderCreateRequests
  };
}
