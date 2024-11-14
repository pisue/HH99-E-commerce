package com.hh99.ecommerce.order.interfaces.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {
    private final Long productId;
    private final int quantity;

    @Builder
    protected OrderCreateRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
