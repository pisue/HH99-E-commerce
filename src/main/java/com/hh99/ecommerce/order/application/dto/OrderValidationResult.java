package com.hh99.ecommerce.order.application.dto;

import com.hh99.ecommerce.order.domain.dto.CreateOrderItemDto;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderValidationResult {
    private final ProductDomain productDomain;
    private final BigDecimal totalPrice;
    private final CreateOrderItemDto createOrderItemDto;

    @Builder
    protected OrderValidationResult(ProductDomain productDomain, BigDecimal totalPrice, CreateOrderItemDto createOrderItemDto) {
        this.productDomain = productDomain;
        this.totalPrice = totalPrice;
        this.createOrderItemDto = createOrderItemDto;
    }
}
