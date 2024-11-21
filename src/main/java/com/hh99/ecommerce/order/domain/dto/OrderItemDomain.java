package com.hh99.ecommerce.order.domain.dto;

import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.interfaces.response.OrderItemResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderItemDomain {
    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final Integer quantity;
    private final BigDecimal itemPrice;
    private final LocalDateTime createdAt;

    @Builder
    protected OrderItemDomain(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.createdAt = createdAt;
    }

    public OrderItem generateOrderItem() {
        return OrderItem.builder()
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OrderItem toEntity() {
        return OrderItem.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .createdAt(this.createdAt)
                .build();
    }

    public OrderItemResponse toResponse() {
        return OrderItemResponse.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .build();
    }
}
