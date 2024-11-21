package com.hh99.ecommerce.order.infra.repository;

import com.hh99.ecommerce.order.domain.enums.OutboxStatus;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, Integer> {
    List<OrderOutbox> findByStatus(OutboxStatus status);
    Optional<OrderOutbox> findByOrderId(Long orderId);

    List<OrderOutbox> findAllByStatusAndCreatedAtBefore(OutboxStatus outboxStatus, LocalDateTime createAt);
}
