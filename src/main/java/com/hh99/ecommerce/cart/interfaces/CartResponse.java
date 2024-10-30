package com.hh99.ecommerce.cart.interfaces;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CartResponse {
    private final Long id;
    private final Long userId;
    private final Long productId;
    private final String productName;
    private final BigDecimal productPrice;
    private final Integer quantity;
    private final LocalDateTime createdAt;

    @Builder
    protected CartResponse(Long id, Long userId, Long productId, String productName, BigDecimal productPrice, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
}
