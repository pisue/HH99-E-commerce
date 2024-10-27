package com.hh99.ecommerce.balance.infra.repository;

import com.hh99.ecommerce.balance.infra.entity.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}
