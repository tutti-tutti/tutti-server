package com.tutti.server.core.cart.api;

import com.tutti.server.core.cart.payload.request.CartItemsCreateRequest;
import com.tutti.server.core.cart.payload.response.CartItemResponse;
import com.tutti.server.core.member.application.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Cart", description = "장바구니 관련 API")
@SecurityRequirement(name = "Bearer Authentication")
public interface CartApiSpec {

    @Operation(summary = "장바구니 상품 추가")
    void addCartItems(CartItemsCreateRequest request, CustomUserDetails user);

    @Operation(summary = "장바구니 상품 조회")
    List<CartItemResponse> getCartItems(CustomUserDetails user);

    @Operation(summary = "장바구니 상품 삭제")
    void removeCartItem(@Parameter(description = "삭제할 장바구니 상품 id", example = "1") Long cartItemId,
            CustomUserDetails user);
}
