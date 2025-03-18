package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 생성 응답")
public record ReviewCreateResponse(

    @Schema(description = "리뷰 ID", example = "1")
    Long reviewId,
    @Schema(description = "리뷰 작성자 ID", example = "1")
    Long memberId,
    @Schema(description = "상품 ID", example = "1")
    Long productId,
    @Schema(description = "리뷰 작성자 닉네임", example = "tutti")
    String nickname,
    @Schema(description = "상품 평점 (1~5)", example = "5")
    int rating,
    @Schema(description = "리뷰 내용", example = "좋아요!")
    String content,
    @Schema(description = "리뷰 이미지 URL 목록 (최대 4개)")
    String reviewImageUrls,
    @Schema(description = "감성 분석 결과 (positive/negative)", example = "positive")
    String sentiment,
    @Schema(description = "감성 분석 정확도", example = "0.9")
    double sentimentProbability,
    @Schema(description = "리뷰 생성일시", example = "2021-08-01T00:00:00")
    String createdAt
) {

}

