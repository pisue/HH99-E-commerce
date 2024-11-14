package com.hh99.ecommerce.order.domain.dto;

import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.interfaces.response.OrderItemResponse;
import com.hh99.ecommerce.order.interfaces.response.OrderResponse;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private final LocalDateTime createDateTime;

    @Builder
    protected OrderItemDomain(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice, LocalDateTime createDateTime) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.createDateTime = createDateTime;
    }

    public OrderItem generateOrderItem() {
        return OrderItem.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .createDateTime(LocalDateTime.now())
                .build();
    }

    public OrderItem toEntity() {
        return OrderItem.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
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
