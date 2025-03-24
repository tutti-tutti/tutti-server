package com.tutti.server.core.cart.application;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.cart.payload.request.CartItemsCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemResponse;
import java.util.List;

public interface CartService {

    void addCartItems(CartItemsCreateRequest request, Long memberId);

    void validateProductItems(List<CartItemsCreateRequest.CartItemRequest> requests);

    void createCartItem(CartItemsCreateRequest.CartItemRequest request, Long memberId);

    List<CartItemResponse> getCartItems(Long memberId);

    void removeCartItem(Long cartItemId, Long memberId);

    CartItem getCartItem(Long cartItemId, Long memberId);
}
