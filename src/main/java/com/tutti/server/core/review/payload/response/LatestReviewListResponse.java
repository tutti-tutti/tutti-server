package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "최신순 리뷰 목록 응답")
public record LatestReviewListResponse(
        @Schema(description = "리뷰 목록") List<ReviewResponse> reviews,
        @Schema(description = "커서 정보", nullable = true) LatestReviewCursor cursor,
        @Schema(description = "다음 페이지 존재 여부", example = "true") boolean hasNext
) {

}
