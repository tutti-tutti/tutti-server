package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품별 별점 평균")
public record ReviewRatingResponse(
        @Schema(description = "상품 아이디", example = "5")
        Long productId,
        @Schema(description = "상품의 평균 평점", example = "4.3")
        String avg
) {

}

