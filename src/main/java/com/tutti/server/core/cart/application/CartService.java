package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import java.util.List;

public interface CartService {

    void addCartItem(Long memberId, CartItemCreateRequest request);

    void createCartItem(Long memberId, CartItemCreateRequest request);

    List<CartItemsResponse> getCartItems(Long memberId);

    void removeCartItem(Long memberId, Long cartItemId);
}
