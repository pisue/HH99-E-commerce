package com.hh99.ecommerce.product.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeductStockInfo {
    Long productId;
    Integer quantity;
}
