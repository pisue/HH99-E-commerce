package com.hh99.ecommerce.cart.domain;

import com.hh99.ecommerce.cart.domain.exception.CartNotFoundException;
import com.hh99.ecommerce.cart.infra.jpa.Cart;
import com.hh99.ecommerce.cart.infra.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void add(CartDomain cartDomain) {
        cartRepository.save(cartDomain.toEntity());
    }

    public void delete(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(CartNotFoundException::new);
        CartDomain cartDomain = cart.toDomain();
        cartDomain.deletedAt();
        cartRepository.save(cartDomain.toEntity());
    }
}
