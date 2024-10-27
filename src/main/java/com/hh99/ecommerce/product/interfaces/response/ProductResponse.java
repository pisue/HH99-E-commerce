package com.hh99.ecommerce.product.interfaces.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int stock;
    private final LocalDateTime regDate;

    @Builder
    protected ProductResponse(Long id, String name, String description, BigDecimal price, int stock, LocalDateTime regDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.regDate = regDate;
    }
}
