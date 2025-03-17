package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 삭제 응답")
public record ReviewDeleteResponse(
    @Schema(description = "삭제 성공 여부", example = "true")
    boolean success,

    @Schema(description = "응답 메시지", example = "리뷰가 성공적으로 삭제되었습니다.")
    String message
) {

}
