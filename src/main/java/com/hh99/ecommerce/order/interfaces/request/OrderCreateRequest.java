package com.hh99.ecommerce.order.interfaces.request;

import com.hh99.ecommerce.product.domain.dto.DeductStockInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long productId;
    private Integer quantity;

    public DeductStockInfo toDeductStockInfo() {
        return DeductStockInfo.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
