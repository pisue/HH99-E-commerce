package com.hh99.ecommerce.order.application.dto;

import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
public class OrderValidationResult {
    private final ProductDomain productDomain;
    private final BigDecimal totalPrice;
    private final CreateOrderDto createOrderDto;

    @Builder
    protected OrderValidationResult(ProductDomain productDomain, BigDecimal totalPrice, CreateOrderDto createOrderDto) {
        this.productDomain = productDomain;
        this.totalPrice = totalPrice;
        this.createOrderDto = createOrderDto;
    }
}
