package com.hh99.ecommerce.order.interfaces.controller;

import com.hh99.ecommerce.order.interfaces.request.CreateOrderRequest;
import com.hh99.ecommerce.order.interfaces.response.OrderResponse;
import com.hh99.ecommerce.order.application.usecase.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements SwaggerOrderController{
    private final OrderUseCase orderUsecase;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequest request) {
        orderUsecase.createOrder(request.getUserId(), request.getProductOrderRequests());
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        return orderUsecase.getOrders(userId);
    }

    @Override
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderUsecase.getOrder(id);
    }
}
