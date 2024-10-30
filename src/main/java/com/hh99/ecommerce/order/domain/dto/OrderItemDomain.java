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

@Getter
public class OrderItemDomain {
    private final Long id; // 주문 상세 ID
    private final Long orderId; // 주문 ID
    private final Long productId; // 상품 ID
    private final Integer quantity; // 주문 수량
    private final BigDecimal itemPrice; // 상품 가격

    @Builder
    protected OrderItemDomain(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
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
