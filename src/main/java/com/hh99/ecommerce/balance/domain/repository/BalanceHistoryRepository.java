package com.hh99.ecommerce.balance.domain.repository;

import com.hh99.ecommerce.balance.domain.entity.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}
