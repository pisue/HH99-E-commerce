package com.hh99.ecommerce.order.controller;

import com.hh99.ecommerce.order.controller.request.CreateOrderRequest;
import com.hh99.ecommerce.order.controller.response.CreateOrderResponse;
import com.hh99.ecommerce.order.controller.response.OrderItemResponse;
import com.hh99.ecommerce.order.controller.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/orders")
@Tag(name = "주문 / 결제 API")
public class OrderController {

    @PostMapping
    @Operation(summary = "주문 요청")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

        BigDecimal userBalance = new BigDecimal("1000.00"); // 실제로는 서비스에서 사용자 잔액을 확인해야 함
        BigDecimal orderTotal = createOrderRequest.getTotalPrice();

        if (orderTotal.compareTo(userBalance) > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CreateOrderResponse("잔액이 부족합니다.", null));
        }

        return ResponseEntity.ok(new CreateOrderResponse("주문이 완료", 1L));
    }

    @GetMapping("{userId}")
    @Operation(summary = "유저 주문 목록 조회")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable Long userId) {
        OrderResponse ordersResponse = new OrderResponse(1L, LocalDateTime.now(), new BigDecimal(100000));
        OrderResponse ordersResponse2 = new OrderResponse(1L, LocalDateTime.now(), new BigDecimal(100000));

        return ResponseEntity.ok(List.of(ordersResponse, ordersResponse2));
    }

    @GetMapping("{userId}/{orderId}")
    @Operation(summary = "주문정보 상세 조회")
    public ResponseEntity<List<OrderItemResponse>> getOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderItemResponse orderItemResponse = new OrderItemResponse(1L, 1L, 3L, 2, new BigDecimal(10000));
        OrderItemResponse orderItemResponse2 = new OrderItemResponse(2L, 1L, 4L, 1, new BigDecimal(23000));
        OrderItemResponse orderItemResponse3 = new OrderItemResponse(3L, 1L, 8L, 5, new BigDecimal(50000));

        return ResponseEntity.ok(List.of(orderItemResponse, orderItemResponse2, orderItemResponse3));
    }



}
