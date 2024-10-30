package com.hh99.ecommerce.balance.interfaces.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceResponse {
    private Long id;
    private Long userId;
    private BigDecimal amount;
}
