package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import java.util.List;

public interface CartService {

    void addCartItems(CartItemCreateRequest request, Long memberId);

    void validateProductItems(List<CartItemCreateRequest.CartItemRequest> requests);

    void createCartItem(CartItemCreateRequest.CartItemRequest request, Long memberId);

    List<CartItemsResponse> getCartItems(Long memberId);

    void removeCartItem(Long cartItemId, Long memberId);

    CartItem getCartItem(Long cartItemId, Long memberId);
}
