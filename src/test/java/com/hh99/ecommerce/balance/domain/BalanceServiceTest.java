package com.hh99.ecommerce.balance.domain;

import com.hh99.ecommerce.balance.domain.dto.BalanceDomain;
import com.hh99.ecommerce.balance.domain.exception.InvalidAmountException;
import com.hh99.ecommerce.balance.domain.exception.UserBalanceNotFoundException;
import com.hh99.ecommerce.balance.infra.entity.Balance;
import com.hh99.ecommerce.balance.infra.entity.BalanceHistory;
import com.hh99.ecommerce.balance.infra.repository.BalanceHistoryRepository;
import com.hh99.ecommerce.balance.infra.repository.BalanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceHistoryRepository balanceHistoryRepository;

    @Test
    @DisplayName("포인트 충전 성공")
    void charge_success() {
        // Given
        Long userId = 1L;
        BigDecimal chargeAmount = BigDecimal.valueOf(1000);

        Balance balance = Balance.builder()
                .id(1L)
                .userId(userId)
                .amount(BigDecimal.ZERO)
                .build();

        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));

        // When
        balanceService.charge(userId, chargeAmount);

        // Then
        verify(balanceRepository).findByUserId(userId);
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository).save(any(BalanceHistory.class));
        assertEquals(chargeAmount, balance.getAmount());
    }

    @Test
    @DisplayName("신규 사용자 포인트 충전 성공")
    void charge_success_new_user() {
        // Given
        Long userId = 1L;
        BigDecimal chargeAmount = BigDecimal.valueOf(1000);

        Balance newBalance = Balance.createNewBalance(userId);
        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(balanceRepository.save(any(Balance.class))).thenReturn(newBalance);

        // When
        balanceService.charge(userId, chargeAmount);

        // Then
        verify(balanceRepository).findByUserId(userId);
        verify(balanceRepository, times(1)).save(any(Balance.class));  // 신규 생성 + 충전
        verify(balanceHistoryRepository).save(any(BalanceHistory.class));
    }

    @Test
    @DisplayName("잘못된 충전 금액으로 인한 충전 실패")
    void charge_fail_invalid_amount() {
        // Given
        Long userId = 1L;
        BigDecimal invalidAmount = BigDecimal.valueOf(-1000);

        // When & Then
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> balanceService.charge(userId, invalidAmount)
        );

        verify(balanceRepository, never()).findByUserId(anyLong());
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository, never()).save(any(BalanceHistory.class));
        assertEquals("충전 금액은 양수여야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("0원 충전 시도로 인한 충전 실패")
    void charge_fail_zero_amount() {
        // Given
        Long userId = 1L;
        BigDecimal zeroAmount = BigDecimal.ZERO;

        // When & Then
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> balanceService.charge(userId, zeroAmount)
        );

        verify(balanceRepository, never()).findByUserId(anyLong());
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository, never()).save(any(BalanceHistory.class));
        assertEquals("충전 금액은 양수여야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유저 잔액 조회 성공")
    void getBalance() {
        //Given
        Long userId = 1L;
        Balance balance = Balance.builder()
                .id(1L)
                .userId(userId)
                .amount(new BigDecimal("10000"))
                .build();
        when(balanceRepository.findById(userId)).thenReturn(Optional.of(balance));
        //When
        BalanceDomain balanceDomain = balanceService.getBalance(userId);
        Balance result = balanceDomain.toEntity();

        //Then
        verify(balanceRepository).findById(userId);
        assertEquals(result.getId(), balance.getId());
    }

    @Test
    @DisplayName("포인트 차감 성공")
    void deductBalance_success() {
        //Given
        Long userId = 1L;
        BigDecimal deductAmount = BigDecimal.valueOf(1000);
        Balance balance = Balance.builder()
                .id(1L)
                .userId(userId)
                .amount(new BigDecimal("10000"))
                .build();
        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));
        //When
        balanceService.deduct(userId, deductAmount);

        //Then
        verify(balanceRepository).findByUserId(userId);
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository).save(any(BalanceHistory.class));
        assertEquals(new BigDecimal(9000), balance.getAmount());
    }

    @Test
    @DisplayName("잘못된 사용 금액으로 인한 충전 실패")
    void deduct_fail_invalid_amount() {
        // Given
        Long userId = 1L;
        BigDecimal invalidAmount = BigDecimal.valueOf(-1000);

        // When & Then
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> balanceService.deduct(userId, invalidAmount)
        );

        verify(balanceRepository, never()).findByUserId(anyLong());
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository, never()).save(any(BalanceHistory.class));
        assertEquals("충전 금액은 양수여야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("0원 차감 시도로 인한 차감 실패")
    void deduct_fail_zero_amount() {
        // Given
        Long userId = 1L;
        BigDecimal invalidAmount = BigDecimal.valueOf(0);

        // When & Then
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> balanceService.deduct(userId, invalidAmount)
        );

        verify(balanceRepository, never()).findByUserId(anyLong());
        verify(balanceRepository, never()).save(any(Balance.class));
        verify(balanceHistoryRepository, never()).save(any(BalanceHistory.class));
        assertEquals("충전 금액은 양수여야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("신규 사용자 차감 시도 할 때 실패")
    void deduct_fail_new_user() {
        //Given
        Long userId = 1L;
        when(balanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

        //When
        UserBalanceNotFoundException exception = assertThrows(
                UserBalanceNotFoundException.class,
                () -> balanceService.deduct(userId, new BigDecimal("2000"))
        );

        verify(balanceRepository, times(1)).findByUserId(anyLong());
        verify(balanceHistoryRepository, never()).save(any(BalanceHistory.class));
        assertEquals("사용자의 잔액정보를 찾을 수 없습니다.", exception.getMessage());
    }
}