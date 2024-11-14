package com.hh99.ecommerce.order.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class CreateOrderItemDto {
    private final Long userId;
    private final Long productId;
    private final int quantity;
    private final BigDecimal price;

    @Builder
    protected CreateOrderItemDto(Long userId, Long productId, int quantity, BigDecimal price) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemDomain generateOrderItemDomain(Long orderId) {
        return OrderItemDomain.builder()
                .orderId(orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.price)
                .build();
    }
}
