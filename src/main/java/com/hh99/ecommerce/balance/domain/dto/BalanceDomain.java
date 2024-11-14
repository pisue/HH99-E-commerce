package com.hh99.ecommerce.balance.domain.dto;

import com.hh99.ecommerce.balance.infra.entity.Balance;
import com.hh99.ecommerce.balance.interfaces.response.BalanceResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BalanceDomain {
    private final Long id;
    private final Long userId;
    private final BigDecimal amount;

    @Builder
    protected BalanceDomain(Long id, Long userId, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public Balance generateBalance() {
        return Balance.builder()
                .userId(this.userId)
                .amount(this.amount)
                .build();
    }

    public Balance toEntity() {
        return Balance.builder()
                .id(this.id)
                .userId(this.userId)
                .amount(this.amount)
                .build();
    }

    public BalanceResponse toResponse() {
        return BalanceResponse.builder()
                .id(this.id)
                .userId(this.userId)
                .amount(this.amount)
                .build();
    }
}
