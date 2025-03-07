package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.payload.request.CartItemRequest;

public interface CartService {

    void createCartItem(CartItemRequest cartItemRequest);
}
