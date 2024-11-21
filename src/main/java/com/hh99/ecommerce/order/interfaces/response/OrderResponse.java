package com.hh99.ecommerce.order.interfaces.response;

import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
   private final Long id;
   private final LocalDateTime orderDate;
   private final BigDecimal totalPrice;
   private final OrderStatus status;
   private final List<OrderItemResponse> orderItems;

   @Builder
   protected OrderResponse(Long id, LocalDateTime orderDate, BigDecimal totalPrice, OrderStatus status, List<OrderItemResponse> orderItems) {
      this.id = id;
      this.orderDate = orderDate;
      this.status = status;
      this.totalPrice = totalPrice;
      this.orderItems = orderItems;
   }
}
