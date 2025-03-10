package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.payload.request.CartItemRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import java.util.List;

public interface CartService {

    public List<CartItemsResponse> getCartItems(Long memberId);

    public void addCartItem(Long memberId, CartItemRequest request);

    public void createCartItem(Long memberId, CartItemRequest request);
}
