package com.hh99.ecommerce.product.infra;

import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.domain.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder
    protected Product(Long id, String name, String description, BigDecimal price, Integer stock, LocalDateTime regDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.regDate = regDate;
    }

    public ProductDomain toDomain() {
        return ProductDomain.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .regDate(this.regDate)
                .build();
    }


    public void deductStock(int quantity) {
        if (this.stock < quantity) throw new OutOfStockException();
        this.stock = this.stock - quantity;
    }

    public void increaseStock(int quantity) {
        this.stock = this.stock + quantity;
    }
}
