package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.payload.request.CartItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "장바구니 API")
public interface CartApiSpec {

    @Operation(summary = "장바구니 상품 추가")
    void addCartItem(CartItemRequest request);
}
