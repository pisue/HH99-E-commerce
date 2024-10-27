package com.hh99.ecommerce.balance.controller;

import com.hh99.ecommerce.balance.controller.response.BalanceResponse;
import com.hh99.ecommerce.balance.controller.request.ChargeBalanceRequest;
import com.hh99.ecommerce.balance.application.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public BalanceResponse getBalance(@PathVariable Long userId) {
        return balanceService.getBalance(userId);
    }

    @Override
    @PatchMapping("/{userId}")
    public void deductBalance(@PathVariable Long userId,@RequestBody BigDecimal amount) {
        balanceService.deductBalance(userId, amount);
    }


}
