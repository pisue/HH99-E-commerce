package com.hh99.ecommerce.order.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CreateOrderDto {
    Long userId;
    Long productId;
    int quantity;
    BigDecimal price;
}