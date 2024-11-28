package com.hh99.ecommerce.cart.domain;

import com.hh99.ecommerce.cart.infra.jpa.Cart;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartDomain {
    private final Long id;
    private final Long userId;
    private final Long productId;
    private final Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    protected CartDomain(Long id, Long userId, Long productId, Integer quantity, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static CartDomain generateCartDomain(Long userId, Long productId, Integer quantity) {
        return CartDomain.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .createdAt(LocalDateTime.now())
                .build();
    }
    public Cart generateCart() {
        return Cart.builder()
                .userId(this.userId)
                .quantity(this.quantity)
                .productId(this.productId)
                .build();
    }

    public Cart toEntity() {
        return Cart.builder()
                .id(this.id)
                .userId(this.userId)
                .quantity(this.quantity)
                .productId(this.productId)
                .createdAt(this.createdAt)
                .deletedAt(this.deletedAt)
                .build();
    }

    public void deletedAt() {
        this.deletedAt = LocalDateTime.now();
    }
}
