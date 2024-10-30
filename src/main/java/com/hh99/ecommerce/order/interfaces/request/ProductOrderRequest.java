package com.hh99.ecommerce.order.interfaces.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductOrderRequest {
    private final Long productId;
    private final int quantity;

    @Builder
    protected ProductOrderRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
