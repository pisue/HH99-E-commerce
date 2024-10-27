package com.hh99.ecommerce.balance.interfaces.response;

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
