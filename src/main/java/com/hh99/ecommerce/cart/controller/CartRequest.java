package com.hh99.ecommerce.cart.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
