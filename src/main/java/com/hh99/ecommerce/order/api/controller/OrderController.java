package com.hh99.ecommerce.order.api.controller;

import com.hh99.ecommerce.order.api.request.CreateOrderRequest;
import com.hh99.ecommerce.order.api.response.OrderItemResponse;
import com.hh99.ecommerce.order.api.response.OrderResponse;
import com.hh99.ecommerce.order.application.usecase.CreateOrderUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements SwaggerOrderController{
    private final CreateOrderUsecase createOrderUsecase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequest request) {

        //상품에서 바로 주문
        //하나의 상품ID와 수량을 받는다
        //상품을 상세검색한다 재고확인 -> 결재 -> 재고 차감
        //주문내역 생성, 주문 아이템 정보 저장
        createOrderUsecase.execute(request.getUserId(), request.getProductId(), request.getQuantity());
    }

    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        OrderResponse ordersResponse = new OrderResponse(1L, LocalDateTime.now(), new BigDecimal(100000));
        OrderResponse ordersResponse2 = new OrderResponse(1L, LocalDateTime.now(), new BigDecimal(100000));

        return List.of(ordersResponse, ordersResponse2);
    }

    @GetMapping("{userId}/{orderId}")
    public List<OrderItemResponse> getOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderItemResponse orderItemResponse = new OrderItemResponse(1L, 1L, 3L, 2, new BigDecimal(10000));
        OrderItemResponse orderItemResponse2 = new OrderItemResponse(2L, 1L, 4L, 1, new BigDecimal(23000));
        OrderItemResponse orderItemResponse3 = new OrderItemResponse(3L, 1L, 8L, 5, new BigDecimal(50000));

        return List.of(orderItemResponse, orderItemResponse2, orderItemResponse3);
    }



}
