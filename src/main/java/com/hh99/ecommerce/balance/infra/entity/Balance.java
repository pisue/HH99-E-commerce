package com.hh99.ecommerce.balance.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "balance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balance {
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
        this.amount = this.amount.subtract(deductAmount);
    }
}
