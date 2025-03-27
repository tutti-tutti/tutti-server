package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "평점순 커서 응답")
public record RatingReviewCursorResponse(
        @Schema(description = "리뷰 ID", example = "49") Long reviewId,
        @Schema(description = "리뷰 평점", example = "4.7") float rating
) {

}
