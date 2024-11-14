package com.hh99.ecommerce.order.interfaces.response;

import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
public class OrderItemResponse {
    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final Integer quantity;
    private final BigDecimal itemPrice;
    private final OrderStatus orderStatus;

    @Builder
    protected OrderItemResponse(Long id, Long orderId, Long productId, Integer quantity, BigDecimal itemPrice, OrderStatus orderStatus) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.orderStatus = orderStatus;
    }
}
