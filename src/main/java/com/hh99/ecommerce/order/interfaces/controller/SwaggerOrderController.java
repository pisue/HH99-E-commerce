package com.hh99.ecommerce.order.interfaces.controller;

import com.hh99.ecommerce.order.interfaces.request.CreateOrderRequest;
import com.hh99.ecommerce.order.interfaces.response.CreateOrderResponse;
import com.hh99.ecommerce.order.interfaces.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "주문 / 결제 API")
public interface SwaggerOrderController {
    @Operation(summary = "주문 요청")
    CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

    @Operation(summary = "유저 주문 목록 조회")
    List<OrderResponse> getOrders(@PathVariable Long userId);

    @Operation(summary = "주문정보 상세 조회")
    OrderResponse getOrder(Long id);
}
