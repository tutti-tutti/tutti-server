package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.payload.request.CartItemRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "장바구니 API")
public interface CartApiSpec {

    @Operation(summary = "장바구니 상품 조회")
    public List<CartItemsResponse> getCartItems(Long memberId);

    @Operation(summary = "장바구니 상품 추가")
    public void addCartItem(Long memberId, CartItemRequest request);

    @Operation(summary = "장바구니 상품 삭제")
    public void removeCartItem(Long memberId, Long cartItemId);
}
