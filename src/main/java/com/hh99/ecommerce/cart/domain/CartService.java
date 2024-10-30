package com.hh99.ecommerce.cart.domain;

import com.hh99.ecommerce.cart.domain.exception.CartNotFoundException;
import com.hh99.ecommerce.cart.infra.jpa.Cart;
import com.hh99.ecommerce.cart.infra.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CartDomain> getCarts(Long userId) {
        return cartRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(Cart::toDomain)
                .collect(Collectors.toList());
    }
}
