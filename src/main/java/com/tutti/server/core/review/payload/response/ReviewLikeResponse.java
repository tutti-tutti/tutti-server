package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 도움이 되었어요 평가 응답")
public record ReviewLikeResponse(

    @Schema(description = "리뷰 ID", example = "123")
    Long reviewId,

    @Schema(description = "리뷰 좋아요 수", example = "5")
    int likeCount,

    @Schema(description = "현재 사용자가 좋아요를 눌렀는지 여부", example = "true")
    boolean liked

) {

}
