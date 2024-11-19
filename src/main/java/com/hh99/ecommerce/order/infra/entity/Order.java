package com.hh99.ecommerce.order.infra.entity;

import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;


    @Builder
    protected Order(Long id, Long userId, LocalDateTime orderDate, BigDecimal totalPrice) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public OrderDomain toDomain() {
        return OrderDomain.builder()
                .id(this.id)
                .userId(this.userId)
                .orderDate(this.orderDate)
                .totalPrice(this.totalPrice)
                .build();
    }
}
