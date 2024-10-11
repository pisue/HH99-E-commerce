package com.hh99.ecommerce.balance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@ResponseBody
@RequestMapping("/api/balance")
@Tag(name = "잔액 관리 API")
public class BalanceController {

    @PatchMapping("/charge")
    @Operation(summary = "잔액충전")
    public ResponseEntity<ChargeBalanceResponse> charge(@RequestBody ChargeBalanceRequest chargeBalanceRequest) {
        int newBalance = 2000;
        return ResponseEntity.ok(new ChargeBalanceResponse("충전 성공", newBalance));
    }

    @GetMapping("{userId}")
    @Operation(summary = "잔액 조회")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(new BalanceResponse(1L, 1L, new BigDecimal("10000")));
    }

}
