package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "도움이 되었어요 순 리뷰 목록 응답")
public record LikeReviewListResponse(
        @Schema(description = "리뷰 목록") List<ReviewResponse> reviews,
        @Schema(description = "커서 정보", nullable = true) LikeReviewCursor cursor,
        @Schema(description = "다음 페이지 존재 여부", example = "true") boolean hasNext
) {

}
