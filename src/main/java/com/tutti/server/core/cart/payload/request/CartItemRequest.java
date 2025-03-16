package com.tutti.server.core.cart.payload.request;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemRequest(

    @NotNull(message = "필수 옵션을 선택해주세요.")
    Long productItemId,

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    int quantity

) {

  public CartItem toEntity(Member member, ProductItem productItem) {
    // 이 빌더는 CartItem 의 빌더
    return CartItem.builder()
        .member(member)
        .productItem(productItem)
        .productItemOption(productItem.getOptionName())
        .productItemOption(productItem.getOptionValue())
        .productName(productItem.getProduct().getName())
        .productImgUrl(productItem.getProduct().getTitleUrl())
        .quantity(quantity)
        .price(productItem.getSellingPrice())
//                .soldOut(productItem.getSoldOut())
        .build();
  }
}
