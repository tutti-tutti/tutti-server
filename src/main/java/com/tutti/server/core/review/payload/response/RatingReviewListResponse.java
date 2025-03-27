package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "평점순 리뷰 목록 응답")
public record RatingReviewListResponse(
        @Schema(description = "리뷰 목록") List<ReviewResponse> reviews,
        @Schema(description = "커서 정보", nullable = true) RatingReviewCursor cursor,
        @Schema(description = "다음 페이지 존재 여부", example = "true") boolean hasNext
) {

}
