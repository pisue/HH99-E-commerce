package com.hh99.ecommerce.order.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
   private Long id;
   private LocalDateTime orderDate;
   private BigDecimal totalPrice;
}
