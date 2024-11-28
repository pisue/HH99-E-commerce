package com.hh99.ecommerce.product.domain.dto;

import com.hh99.ecommerce.product.application.model.ProductInfo;
import com.hh99.ecommerce.product.infra.Product;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 추가
public class ProductDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private LocalDateTime regDate;

    @Builder
    protected ProductDomain(Long id, String name, String description, BigDecimal price, int stock, LocalDateTime regDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.regDate = regDate;
    }

    public Product generateProduct() {
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .regDate(LocalDateTime.now())
                .build();
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

    public ProductInfo toProductInfo() {
        return ProductInfo.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .regDate(this.regDate)
                .build();
    }
}
