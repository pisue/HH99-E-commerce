package com.hh99.ecommerce.balance.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeBalanceResponse {
    private String message;
    private int newBalance;
}
