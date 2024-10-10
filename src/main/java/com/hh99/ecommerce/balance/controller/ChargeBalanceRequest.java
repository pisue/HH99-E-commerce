package com.hh99.ecommerce.balance.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeBalanceRequest {
    private Long userId;
    private int amount;
}

