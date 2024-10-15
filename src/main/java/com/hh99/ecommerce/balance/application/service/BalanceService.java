package com.hh99.ecommerce.balance.application.service;

import com.hh99.ecommerce.balance.application.exception.InvalidAmountException;
import com.hh99.ecommerce.balance.application.exception.UserNotFoundException;
import com.hh99.ecommerce.balance.domain.*;
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

        Balance balance = balanceRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        
        // 잔액 업데이트
        balance.charge(amount);
        balanceRepository.save(balance);
        
        // 히스토리 저장
        BalanceHistory history = BalanceHistory.create(balance.getId(), BalanceHistoryType.CHARGE, amount);
        balanceHistoryRepository.save(history);
    }
}
