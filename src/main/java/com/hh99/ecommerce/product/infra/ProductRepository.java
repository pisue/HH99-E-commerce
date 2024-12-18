package com.hh99.ecommerce.product.infra;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p
        JOIN OrderItem oi ON p.id = oi.productId
        WHERE oi.createdAt BETWEEN :startDateTime AND :endDateTime
        GROUP BY oi.productId
        ORDER BY SUM(oi.quantity) DESC
        LIMIT :topNumber
    """)
    List<Product> findPopularProducts(
            @Param("topNumber") Integer topNumber,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdWithPessimisticLock(@Param("id") Long id);
}
