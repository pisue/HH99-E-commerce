package com.hh99.ecommerce.order.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private List<OrderDetail> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetail {
        private Long id;
        private Long productId;
        private int quantity;
        private BigDecimal totalPrice;
    }
}

