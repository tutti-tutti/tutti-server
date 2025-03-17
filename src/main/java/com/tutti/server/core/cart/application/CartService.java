package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import java.util.List;

public interface CartService {

    List<CartItemsResponse> getCartItems(Long memberId);

    void addCartItem(CartItemCreateRequest request);

    void createCartItem(CartItemCreateRequest request);

    void removeCartItem(Long cartItemId, Long memberId);
}
