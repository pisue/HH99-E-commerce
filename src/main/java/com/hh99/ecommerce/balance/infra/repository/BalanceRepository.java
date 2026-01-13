package com.hh99.ecommerce.balance.infra.repository;

import com.hh99.ecommerce.balance.infra.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Balance b where b.userId = :userId")
    Optional<Balance> findByUserIdWithLock(@Param("userId") Long userId);
}
