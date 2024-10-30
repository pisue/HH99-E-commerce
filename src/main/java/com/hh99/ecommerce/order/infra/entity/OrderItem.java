package com.hh99.ecommerce.order.infra.entity;

import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 상세 ID

    @Column(nullable = false)
    private Long orderId; // 주문 ID

    @Column(nullable = false)
    private Long productId; // 상품 ID

    @Column(nullable = false)
    private Integer quantity; // 주문 수량

    @Column(nullable = false)
    private BigDecimal itemPrice; // 상품 가격

    @Builder
    protected OrderItem(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public OrderItemDomain toDomain() {
        return OrderItemDomain.builder()
                .id(this.id)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .itemPrice(this.itemPrice)
                .build();
    }
}
