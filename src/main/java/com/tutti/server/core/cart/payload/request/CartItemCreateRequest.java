package com.tutti.server.core.cart.payload.request;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemCreateRequest(

        @NotNull(message = "필수 옵션을 선택해주세요.")
        Long productItemId,

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        @Max(value = 10, message = "최대 10개까지 주문 가능합니다.")
        int quantity

) {

    public CartItem toEntity(Member member, ProductItem productItem) {
        // 이 빌더는 CartItem 의 빌더
        return CartItem.builder()
                .member(member)
                .productItem(productItem)
                .productName(productItem.getProduct().getName())
                .productImgUrl(productItem.getProduct().getTitleUrl())
                .firstOptionName(productItem.getFirstOptionName())
                .firstOptionValue(productItem.getFirstOptionValue())
                .secondOptionName(productItem.getSecondOptionName())
                .secondOptionValue(productItem.getSecondOptionValue())
                .quantity(quantity)
                .originalPrice(productItem.getProduct().getOriginalPrice())
                .sellingPrice(productItem.getSellingPrice())
                .soldOut(productItem.isSoldOut())
                .build();
    }
}
