package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최신순 커서 요청")
public record LatestReviewCursor(
        @Schema(description = "마지막 리뷰 ID", example = "32", nullable = true)
        Long reviewId
) {

}
