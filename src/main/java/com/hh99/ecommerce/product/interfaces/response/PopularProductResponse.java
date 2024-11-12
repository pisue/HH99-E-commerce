package com.hh99.ecommerce.product.interfaces.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularProductResponse {
    private final Integer rank;
    private final ProductResponse productResponse;

    @Builder
    protected PopularProductResponse(Integer rank, ProductResponse productResponse) {
        this.rank = rank;
        this.productResponse = productResponse;
    }
}
