package com.hh99.ecommerce.balance.api.controller;

import com.hh99.ecommerce.balance.api.response.BalanceResponse;
import com.hh99.ecommerce.balance.api.response.ChargeBalanceResponse;
import com.hh99.ecommerce.balance.api.request.ChargeBalanceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Balance API", description = "잔액 충전 및 조회와 관련된 API")
public interface SwaggerBalanceController {
    @Operation(summary = "잔액 충전", description = "결제에 사용될 금액을 충전하는 API")
    void charge(@RequestBody ChargeBalanceRequest chargeBalanceRequest);


    @Operation(summary = "잔액 조회", description = "사용자 식별자를 통해 해당 사용자의 잔액 조회")
    ResponseEntity<BalanceResponse> getBalance(@PathVariable Long userId);
}
