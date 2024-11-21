package com.hh99.ecommerce.order.infra.entity;

import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @Builder
    protected Order(Long id, Long userId, LocalDateTime orderDate, BigDecimal totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public OrderDomain toDomain() {
        return OrderDomain.builder()
                .id(this.id)
                .userId(this.userId)
                .orderDate(this.orderDate)
                .totalPrice(this.totalPrice)
                .orderStatus(this.orderStatus)
                .build();
    }
}
