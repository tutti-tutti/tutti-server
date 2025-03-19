package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 상세 조회 요청 파라미터")
public class ReviewDetailRequest {

    @Schema(description = "리뷰 ID", example = "1")
    private long reviewId;

    public Long getReviewId() {
        return reviewId;
    }
}
