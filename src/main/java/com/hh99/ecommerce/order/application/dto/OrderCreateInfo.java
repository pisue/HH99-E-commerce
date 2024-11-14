package com.hh99.ecommerce.order.application.dto;

import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderCreateInfo {
    private final ProductDomain productDomain;
    private final BigDecimal totalPrice;
    private final OrderItemCreateInfo orderItemCreateInfo;

    @Builder
    protected OrderCreateInfo(ProductDomain productDomain, BigDecimal totalPrice, OrderItemCreateInfo orderItemCreateInfo) {
        this.productDomain = productDomain;
        this.totalPrice = totalPrice;
        this.orderItemCreateInfo = orderItemCreateInfo;
    }
}
