package com.hh99.ecommerce.balance.domain;

import com.hh99.ecommerce.balance.domain.dto.BalanceDomain;
import com.hh99.ecommerce.balance.domain.exception.BalanceNotEnoughException;
import com.hh99.ecommerce.balance.domain.exception.InvalidAmountException;
import com.hh99.ecommerce.balance.domain.exception.UserBalanceNotFoundException;
import com.hh99.ecommerce.balance.infra.entity.Balance;
import com.hh99.ecommerce.balance.infra.entity.BalanceHistory;
import com.hh99.ecommerce.balance.domain.enums.BalanceHistoryType;
import com.hh99.ecommerce.balance.infra.repository.BalanceHistoryRepository;
import com.hh99.ecommerce.balance.infra.repository.BalanceRepository;
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

        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException();

        Balance balance = balanceRepository.findByUserIdWithLock(userId)
                .orElseGet(() -> balanceRepository.save(Balance.createNewBalance(userId)));
        
        balance.charge(amount);

        BalanceHistory history = BalanceHistory.create(balance.getId(), BalanceHistoryType.CHARGE, amount);
        balanceHistoryRepository.save(history);
    }

    public BalanceDomain getBalance(Long userId) {
       return balanceRepository.findByUserId(userId)
               .map(Balance::toDomain)
               .orElseGet(() -> balanceRepository.save(Balance.createNewBalance(userId)).toDomain());
    }

        @Transactional

        public void deduct(Long userId, BigDecimal amount) {

            if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException();

    

            Balance balance = balanceRepository.findByUserIdWithLock(userId)

                    .orElseThrow(UserBalanceNotFoundException::new);

    

            balance.deduct(amount);

    

            BalanceHistory history = BalanceHistory.create(balance.getId(), BalanceHistoryType.DEDUCT, amount);

            balanceHistoryRepository.save(history);

        }

    

        @Transactional

        public void compensate(Long userId, BigDecimal amount) {

            if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException();

    

            Balance balance = balanceRepository.findByUserIdWithLock(userId)

                    .orElseThrow(UserBalanceNotFoundException::new);

    

            balance.charge(amount); // 차감된 금액 복구

    

            // 이력에는 CHARGE로 남기되, 추후 구분이 필요하면 Type을 추가해야 함. 현재는 CHARGE로 기록.

            BalanceHistory history = BalanceHistory.create(balance.getId(), BalanceHistoryType.CHARGE, amount);

            balanceHistoryRepository.save(history);

        }

    }
