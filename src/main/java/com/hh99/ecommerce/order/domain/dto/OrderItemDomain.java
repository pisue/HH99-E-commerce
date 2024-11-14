package com.hh99.ecommerce.order.domain.dto;

import com.hh99.ecommerce.order.domain.enums.OrderStatus;
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
    private final OrderStatus orderStatus;

    @Builder
    protected OrderItemDomain(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice, LocalDateTime createdAt, OrderStatus orderStatus) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    public OrderItem generateOrderItem() {
        return OrderItem.builder()
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.COMPLETED)
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
                .orderStatus(this.orderStatus)
                .build();
    }

    public OrderItemResponse toResponse() {
        return OrderItemResponse.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .orderStatus(this.orderStatus)
                .build();
    }
}
