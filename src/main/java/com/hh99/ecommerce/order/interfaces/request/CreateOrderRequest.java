package com.hh99.ecommerce.order.interfaces.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequest {
    private Long userId;
    private List<OrderCreateRequest> orderCreateRequests;
}

