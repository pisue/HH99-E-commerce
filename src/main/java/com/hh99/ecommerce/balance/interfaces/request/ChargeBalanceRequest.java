package com.hh99.ecommerce.balance.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeBalanceRequest {
    private Long userId;
    private BigDecimal amount;
}

