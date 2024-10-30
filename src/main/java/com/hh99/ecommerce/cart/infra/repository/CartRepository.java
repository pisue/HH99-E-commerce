package com.hh99.ecommerce.cart.infra.repository;

import com.hh99.ecommerce.cart.domain.CartDomain;
import com.hh99.ecommerce.cart.infra.jpa.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserIdAndDeletedAtIsNull(Long userId);
}
