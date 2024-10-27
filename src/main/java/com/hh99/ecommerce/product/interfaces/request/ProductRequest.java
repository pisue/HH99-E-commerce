package com.hh99.ecommerce.product.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
}