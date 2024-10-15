package com.hh99.ecommerce.balance.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponse {
    private Long id;
    private Long userId;
    private BigDecimal amount;
}
