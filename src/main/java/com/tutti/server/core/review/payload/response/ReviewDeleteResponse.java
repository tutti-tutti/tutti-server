package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 삭제 응답")
public record ReviewDeleteResponse(
    @Schema(description = "삭제된 리뷰의 ID") Long reviewId,
    @Schema(description = "삭제 상태 메시지") String message) {

}
