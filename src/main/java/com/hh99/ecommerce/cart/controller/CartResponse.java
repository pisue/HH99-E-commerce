package com.hh99.ecommerce.cart.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;
}
