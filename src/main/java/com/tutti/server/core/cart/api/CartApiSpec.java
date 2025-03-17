package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.payload.request.CartItemCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "장바구니 API")
public interface CartApiSpec {

    @Operation(summary = "장바구니 상품 조회")
    List<CartItemsResponse> getCartItems(Long memberId);

    @Operation(summary = "장바구니 상품 추가")
    void addCartItem(CartItemCreateRequest request);

    @Operation(summary = "장바구니 상품 삭제")
    void removeCartItem(Long cartItemId, Long memberId);
}
