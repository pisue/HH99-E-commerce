package com.hh99.ecommerce.product.application.model;

import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductInfo {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;
    private final LocalDateTime regDate;

    @Builder
    protected ProductInfo(Long id, String name, String description, BigDecimal price, Integer stock, LocalDateTime regDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.regDate = regDate;
    }

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .regDate(this.regDate)
                .build();
    }

}
