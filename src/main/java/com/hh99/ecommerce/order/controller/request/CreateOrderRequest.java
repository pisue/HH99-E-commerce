package com.hh99.ecommerce.order.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Long userId;
    private List<CreateOrderItemRequest> createOrderItemRequests;
    private BigDecimal totalPrice;
}

