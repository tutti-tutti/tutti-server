package com.tutti.server.core.review.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(
    @NotNull(message = "상품 평점은 필수입니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")  // 평점 최소 1점
    @Max(value = 5, message = "평점은 5 이하이어야 합니다.")  // 평점 최대 5점
    Integer rating,  // 평점 (1~5)

    @NotNull(message = "리뷰 내용은 필수입니다.")
    String content,  // 리뷰 내용

    @NotNull(message = "주문 아이템 ID는 필수입니다.")
    Long orderItemId  // 주문 아이템 ID
) {

}
