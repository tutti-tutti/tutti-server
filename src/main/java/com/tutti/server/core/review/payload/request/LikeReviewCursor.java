package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "도움이 되었어요 순 커서 요청")
public record LikeReviewCursor(
        @Schema(description = "마지막 리뷰 ID", example = "32", nullable = true)
        Long reviewId,

        @Schema(description = "마지막 리뷰 좋아요 수", example = "10", nullable = true)
        Integer likeCount
) {

}
