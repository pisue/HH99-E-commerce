package com.hh99.ecommerce.cart.interfaces;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
