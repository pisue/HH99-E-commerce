package com.hh99.ecommerce.product.domain.dto;

import com.hh99.ecommerce.product.infra.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductDomain {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int stock;
    private final LocalDateTime regDate;

    @Builder
    protected ProductDomain(Long id, String name, String description, BigDecimal price, int stock, LocalDateTime regDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.regDate = regDate;
    }

    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .regDate(this.regDate)
                .build();
    }
}
