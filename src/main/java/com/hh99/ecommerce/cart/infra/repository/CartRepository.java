package com.hh99.ecommerce.cart.infra.repository;

import com.hh99.ecommerce.cart.infra.jpa.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
