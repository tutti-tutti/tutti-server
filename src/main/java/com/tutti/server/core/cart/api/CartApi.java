package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.application.CartService;
import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
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
    public void addCartItem(@RequestBody @Valid CartItemCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        cartService.addCartItems(request, user.getMemberId());
    }

    @Override
    @GetMapping
    public List<CartItemsResponse> getCartItems(@AuthenticationPrincipal CustomUserDetails user) {
        return cartService.getCartItems(user.getMemberId());
    }

    @Override
    @PatchMapping("/{cartItemId}")
    public void removeCartItem(@PathVariable("cartItemId") Long cartItemId,
            @AuthenticationPrincipal CustomUserDetails user) {
        cartService.removeCartItem(cartItemId, user.getMemberId());
    }
}
