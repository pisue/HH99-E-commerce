package com.hh99.ecommerce.cart.interfaces.controller;

import com.hh99.ecommerce.cart.domain.CartDomain;
import com.hh99.ecommerce.cart.domain.CartService;
import com.hh99.ecommerce.cart.interfaces.CartRequest;
import com.hh99.ecommerce.cart.interfaces.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController implements SwaggerCartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CartRequest request) {
        cartService.add(CartDomain.generateCartDomain(request.getUserId(), request.getProductId(), request.getQuantity()));
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getCarts(@PathVariable Long userId) {
        //TODO : productId로 상품정보 조회 후 Response에 담에서 보내기


        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable Long id) {
        cartService.delete(id);
    }

}
