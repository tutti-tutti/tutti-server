package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "도움이 되었어요 커서 응답")
public record LikeReviewCursorResponse(
        @Schema(description = "리뷰 ID", example = "49") Long reviewId,
        @Schema(description = "리뷰 좋아요 수", example = "10") Integer likeCount
) {

}

