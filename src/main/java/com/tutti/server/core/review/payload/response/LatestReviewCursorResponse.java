package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최신순 커서 응답")
public record LatestReviewCursorResponse(
        @Schema(description = "리뷰 ID", example = "49") Long reviewId
) {

}
