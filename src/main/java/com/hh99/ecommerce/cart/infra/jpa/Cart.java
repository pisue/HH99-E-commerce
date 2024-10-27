package com.hh99.ecommerce.cart.infra.jpa;

import com.hh99.ecommerce.cart.domain.CartDomain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @Builder
    protected Cart (Long id, Long userId, Long productId, Integer quantity, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public CartDomain toDomain() {
        return CartDomain.builder()
                .id(this.id)
                .userId(this.userId)
                .productId(this.productId)
                .quantity(this.quantity)
                .createdAt(this.createdAt)
                .deletedAt(this.deletedAt)
                .build();
    }
}
