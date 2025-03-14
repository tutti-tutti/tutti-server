package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 목록 응답")
public record ReviewListResponse(
    @Schema(description = "리뷰 목록") List<ReviewResponse> reviews,
    @Schema(description = "다음 페이지 조회를 위한 커서 값", example = "123456") String nextCursor
) {

}
