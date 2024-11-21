package com.hh99.ecommerce.order.interfaces.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long productId;
    private Integer quantity;
}
