package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ReviewResponse(
    @Schema(description = "리뷰 ID", example = "1")
    Long id,

    @Schema(description = "상품 ID", example = "1")
    Long productId,

    @Schema(description = "리뷰 작성자 닉네임", example = "tutti")
    String nickname,

    @Schema(description = "리뷰 내용", example = "배송이 빠르고 좋아요!")
    String content,

    @Schema(description = "평점", example = "5")
    Integer rating,

    @Schema(description = "리뷰 감성 분석 결과", example = "positive")
    String sentiment,

    @Schema(description = "리뷰 감성 분석 정확도(%)", example = "95.2")
    Double sentimentProbability,

    @Schema(description = "리뷰 작성일시")
    LocalDateTime createdAt
) {

}
