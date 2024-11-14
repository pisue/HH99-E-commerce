package com.hh99.ecommerce.order.infra.entity;

import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 상세 ID

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 주문 ID

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 ID

    @Column(nullable = false)
    private Integer quantity; // 주문 수량

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice; // 상품 가격

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    protected OrderItem(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice, LocalDateTime createAt, OrderStatus orderStatus) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.createAt = createAt;
        this.orderStatus = orderStatus;
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
