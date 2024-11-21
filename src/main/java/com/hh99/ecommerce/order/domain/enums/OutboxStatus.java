package com.hh99.ecommerce.order.domain.enums;

public enum OutboxStatus {
    INIT,       // 초기 상태
    PUBLISHED,  // Kafka로 발행됨
    FAILED      // 발행 실패
}
