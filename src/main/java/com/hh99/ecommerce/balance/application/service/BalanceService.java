package com.hh99.ecommerce.balance.application.service;

import com.hh99.ecommerce.balance.controller.response.BalanceResponse;
import com.hh99.ecommerce.balance.application.exception.InvalidAmountException;
import com.hh99.ecommerce.balance.application.exception.UserBalanceNotFoundException;
import com.hh99.ecommerce.balance.domain.entity.Balance;
import com.hh99.ecommerce.balance.domain.entity.BalanceHistory;
import com.hh99.ecommerce.balance.domain.entity.BalanceHistoryType;
import com.hh99.ecommerce.balance.domain.repository.BalanceHistoryRepository;
import com.hh99.ecommerce.balance.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;

    @Transactional
    public void charge(Long userId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException();
        }

        Balance balance = balanceRepository.findByUserId(userId).orElseThrow(UserBalanceNotFoundException::new);
        
        balance.charge(amount);
        balanceRepository.save(balance);
        
        BalanceHistory history = BalanceHistory.create(balance.getId(), BalanceHistoryType.CHARGE, amount);
        balanceHistoryRepository.save(history);
    }

    public BalanceResponse getBalance(Long userId) {
       return balanceRepository.findById(userId)
               .map(balance -> BalanceResponse.builder()
                       .amount(balance.getAmount())
                       .userId(balance.getUserId())
                       .id(balance.getId())
                       .build())
               .orElseThrow(UserBalanceNotFoundException::new);
    }

    @Transactional
    public void deductBalance(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException();
        Balance balance = balanceRepository.findByUserId(userId).orElseThrow(UserBalanceNotFoundException::new);
    }
}
