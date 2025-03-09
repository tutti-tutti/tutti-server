package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.payload.request.CartItemRequest;

public interface CartService {

    public void addCartItem(Long memberId, CartItemRequest request);

    public void createCartItem(Long memberId, CartItemRequest request);
}
