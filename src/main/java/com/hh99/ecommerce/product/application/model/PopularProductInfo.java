package com.hh99.ecommerce.product.application.model;

import com.hh99.ecommerce.product.interfaces.response.PopularProductResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularProductInfo {
    private final Integer rank;
    private final ProductInfo productInfo;

    @Builder
    protected PopularProductInfo(Integer rank, ProductInfo productInfo) {
        this.rank = rank;
        this.productInfo = productInfo;
    }

    public PopularProductResponse toResponse() {
        return PopularProductResponse.builder()
                .rank(this.rank)
                .productResponse(this.productInfo.toResponse())
                .build();
    }

}
