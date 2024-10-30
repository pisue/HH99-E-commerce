package com.hh99.ecommerce.order.interfaces.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
public class OrderItemResponse {
    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final Integer quantity;
    private final BigDecimal itemPrice;

    @Builder
    protected OrderItemResponse(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }
}
