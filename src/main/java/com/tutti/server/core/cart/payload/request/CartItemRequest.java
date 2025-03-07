package com.tutti.server.core.cart.payload.request;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemRequest(

        Long memberId,

        @NotNull(message = "필수 옵션을 선택해주세요.")
        Long productItemId,

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        int quantity,

        int price,

        boolean soldOut

) {

    public CartItem toEntity(Member member, ProductItem productItem) {
        return CartItem.builder()
                .member(member)
                .productItem(productItem)
                .quantity(quantity)
                .price(price)
                .soldOut(soldOut)
                .build();
    }
}
