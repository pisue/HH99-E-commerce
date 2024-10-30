package com.hh99.ecommerce.balance.interfaces.controller;

import com.hh99.ecommerce.balance.interfaces.response.BalanceResponse;
import com.hh99.ecommerce.balance.interfaces.request.ChargeBalanceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;

@Tag(name = "Balance API", description = "잔액 충전 및 조회와 관련된 API")
public interface SwaggerBalanceController {
    @Operation(summary = "잔액 충전", description = "결제에 사용될 금액을 충전하는 API")
    void charge(ChargeBalanceRequest chargeBalanceRequest);


    @Operation(summary = "잔액 조회", description = "사용자 식별자를 통해 해당 사용자의 잔액 조회")
    BalanceResponse getBalance(Long userId);

    @Operation(summary = "잔액 사용", description = "유저의 잔액을 사용합니다")
    void deductBalance(Long userId, BigDecimal amount);
}
