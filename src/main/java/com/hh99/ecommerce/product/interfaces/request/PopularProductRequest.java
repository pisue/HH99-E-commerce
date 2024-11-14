package com.hh99.ecommerce.product.interfaces.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularProductRequest {
    private final Integer topNumber;
    private final Integer lastDays;

    @Builder
    protected PopularProductRequest(Integer topNumber, Integer lastDays) {
        this.topNumber = topNumber;
        this.lastDays = lastDays;
    }

}
