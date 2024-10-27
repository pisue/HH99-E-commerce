package com.hh99.ecommerce.balance.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance_id", nullable = false)
    private Long balanceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BalanceHistoryType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static BalanceHistory create(Long balanceId, BalanceHistoryType type, BigDecimal amount) {
        return BalanceHistory.builder()
                .balanceId(balanceId)
                .type(type)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
