package com.hh99.ecommerce.balance.infra.entity;

import com.hh99.ecommerce.balance.domain.dto.BalanceDomain;
import com.hh99.ecommerce.balance.domain.exception.BalanceNotEnoughException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "balance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balance {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;


    @Builder
    protected Balance(Long id, Long userId, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public void charge(BigDecimal chargeAmount) {
        this.amount = this.amount.add(chargeAmount);
    }

    public void deduct(BigDecimal deductAmount) {
        if(this.amount.compareTo(deductAmount) < 0) throw new BalanceNotEnoughException();
        this.amount = this.amount.subtract(deductAmount);
    }

    public static Balance createNewBalance(Long userId) {
        return Balance.builder()
                .userId(userId)
                .amount(BigDecimal.ZERO)
                .build();
    }

    public BalanceDomain toDomain() {
        return BalanceDomain.builder()
                .id(this.id)
                .userId(this.userId)
                .amount(this.amount)
                .build();
    }
}
