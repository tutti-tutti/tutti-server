package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.application.CartService;
import com.tutti.server.core.cart.payload.request.CartItemsCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemResponse;
import com.tutti.server.core.member.application.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public void addCartItems(@RequestBody @Valid CartItemsCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        cartService.addCartItems(request, user.getMemberId());
    }

    @Override
    @GetMapping
    public List<CartItemResponse> getCartItems(@AuthenticationPrincipal CustomUserDetails user) {
        return cartService.getCartItems(user.getMemberId());
    }

    @Override
    @PatchMapping("/{cartItemId}")
    public void removeCartItem(@PathVariable("cartItemId") Long cartItemId,
            @AuthenticationPrincipal CustomUserDetails user) {
        cartService.removeCartItem(cartItemId, user.getMemberId());
    }
}
