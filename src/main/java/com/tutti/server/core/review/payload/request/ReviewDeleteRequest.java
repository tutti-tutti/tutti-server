package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 삭제 요청")
public record ReviewDeleteRequest(

    @Schema(description = "리뷰 ID", example = "1")
    @NotNull(message = "리뷰 ID는 필수입니다.")
    Long reviewId,
    @Schema(description = "사용자 이메일", example = "user@example.com")
    @NotNull(message = "사용자 이메일은 필수입니다.")
    String userEmail
) {

}
