package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "평점순 커서 요청")
public record RatingReviewCursor(
        @Schema(description = "마지막 리뷰 ID", example = "32", nullable = true)
        Long reviewId,

        @Schema(description = "마지막 리뷰 평점", example = "4.5", nullable = true)
        Float rating
) {

}
