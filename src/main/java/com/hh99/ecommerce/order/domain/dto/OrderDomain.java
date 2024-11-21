package com.hh99.ecommerce.order.domain.dto;

import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import com.hh99.ecommerce.order.infra.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDomain {
    private final Long id;
    private final Long userId;
    private final LocalDateTime orderDate;
    private final BigDecimal totalPrice;
    private final OrderStatus orderStatus;

    @Builder
    protected OrderDomain(Long id, Long userId, LocalDateTime orderDate, BigDecimal totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public Order toEntity() {
        return Order.builder()
                .id(this.id)
                .userId(this.userId)
                .orderDate(this.orderDate)
                .totalPrice(this.totalPrice)
                .orderStatus(this.orderStatus)
                .build();
    }

    public Order generateOrder() {
        return Order.builder()
                .id(this.id)
                .userId(this.userId)
                .orderDate(LocalDateTime.now())
                .totalPrice(this.totalPrice)
                .orderStatus(OrderStatus.INIT)
                .build();
    }
}
