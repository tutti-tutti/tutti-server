package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 작성 응답")
public record ReviewCreateResponse(
    @Schema(description = "리뷰 ID", example = "1") long id,
    @Schema(description = "유저 ID", example = "1") long memberId,
    @Schema(description = "상품 ID", example = "1") long productId,
    @Schema(description = "유저 닉네임", example = "tutti") String nickname,
    @Schema(description = "평점", example = "5") int rating,
    @Schema(description = "리뷰 내용", example = "너무 좋아요!") String content,
    @Schema(description = "리뷰 이미지 URL", example = "http://tutti.com") String reviewImageUrls,
    @Schema(description = "감성 분석 결과", example = "positive") String sentiment,
    @Schema(description = "감성 분석 정확도", example = "88.24") double sentimentProbability,
    @Schema(description = "리뷰 작성일", example = "2021-08-01") String createdAt
) {

    public static ReviewCreateResponse from(com.tutti.server.core.review.domain.Review review) {
        return new ReviewCreateResponse(
            review.getId(),
            review.getMember().getId(),
            review.getProductId(),
            review.getNickname(),
            review.getRating(),
            review.getContent(),
            review.getReviewImageUrls(),
            review.getSentiment(),
            review.getSentimentProbability(),
            review.getCreatedAt().toString()
        );
    }
}
