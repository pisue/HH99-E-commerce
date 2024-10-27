package com.hh99.ecommerce.order.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
