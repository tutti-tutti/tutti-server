package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 목록 응답")
public record ReviewListResponse(
    @Schema(description = "리뷰 목록") List<ReviewResponse> reviews,
    @Schema(description = "커서 (다음 페이지 요청 시 사용할 마지막 리뷰 ID, 없으면 null)", nullable = true) Long cursor
) {

}
