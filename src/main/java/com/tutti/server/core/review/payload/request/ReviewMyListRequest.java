package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewMyListRequest(
        @NotNull
        @Schema(description = "닉네임", example = "tutti") String nickname,
        @Min(1) @Max(100) @Schema(description = "요청할 리뷰 개수 (기본값: 20)", example = "20") Integer size,
        @Schema(description = "다음 페이지 조회를 위한 커서 값", example = "0") String cursor
) {

}
