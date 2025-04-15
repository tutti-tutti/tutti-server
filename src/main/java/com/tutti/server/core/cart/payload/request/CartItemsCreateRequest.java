package com.tutti.server.core.cart.payload.request;

import com.tutti.server.core.cart.domain.CartItem;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.product.domain.ProductItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "장바구니 상품 추가 요청 DTO")
public record CartItemsCreateRequest(

        @NotNull(message = "productItemId 값이 없습니다.")
        @Schema(description = "장바구니에 담을 상품 목록",
                example = """
                        [
                            {
                                "productItemId": 155,
                                "quantity": 1,
                            },
                            {
                                "productItemId": 196,
                                "quantity": 1,
                            }
                        ]
                        """
        )
        List<CartItemRequest> cartItems
) {

    public record CartItemRequest(

            @NotNull(message = "필수 옵션을 선택해주세요.")
            @Schema(description = "옵션별 상품 ID", example = "155")
            Long productItemId,

            @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
            @Schema(description = "수량", example = "1")
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
}
