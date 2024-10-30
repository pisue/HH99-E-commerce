package com.hh99.ecommerce.product.interfaces.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularProductRequest {
    private final Integer topNumber;
    private final Integer productNumber;

    @Builder
    protected PopularProductRequest(Integer topNumber, Integer productNumber) {
        this.topNumber = topNumber;
        this.productNumber = productNumber;
    }

}
