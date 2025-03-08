package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.application.CartService;
import com.tutti.server.core.cart.payload.request.CartItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartApi implements CartApiSpec {

    private final CartService cartService;

    @Override
    @PostMapping
    public void addCartItem(Long memberId, @RequestBody @Valid CartItemRequest request) {
        cartService.addCartItem(memberId, request);
    }
}
