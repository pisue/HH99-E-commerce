package com.hh99.ecommerce.order.application.dto;

import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class OrderItemCreateInfo {
    private final Long userId;
    private final Long productId;
    private final int quantity;
    private final BigDecimal price;

    @Builder
    protected OrderItemCreateInfo(Long userId, Long productId, int quantity, BigDecimal price) {
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
