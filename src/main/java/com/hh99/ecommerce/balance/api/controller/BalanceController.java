package com.hh99.ecommerce.balance.api.controller;

import com.hh99.ecommerce.balance.api.response.BalanceResponse;
import com.hh99.ecommerce.balance.api.response.ChargeBalanceResponse;
import com.hh99.ecommerce.balance.api.request.ChargeBalanceRequest;
import com.hh99.ecommerce.balance.application.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@ResponseBody
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController implements SwaggerBalanceController{

    private final BalanceService balanceService;

    @Override
    @PatchMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public void charge(@RequestBody ChargeBalanceRequest chargeBalanceRequest) {
        balanceService.charge(chargeBalanceRequest.getUserId(), chargeBalanceRequest.getAmount());
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(new BalanceResponse(1L, 1L, new BigDecimal("10000")));
    }

}
