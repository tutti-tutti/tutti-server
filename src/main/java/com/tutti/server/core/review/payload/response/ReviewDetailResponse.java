package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 상세 조회 응답")
public record ReviewDetailResponse(
    @NotNull @Schema(description = "리뷰 ID", example = "1") long reviewId,
    @NotNull @Schema(description = "상품 ID", example = "1") long productId,
    @NotNull @Schema(description = "유저 닉네임", example = "tutti") String nickname,
    @Schema(description = "평점", example = "5") int rating,
    @NotNull @Schema(description = "리뷰 내용", example = "너무 좋아요!") String content,
    @Schema(description = "감성 분석 결과", example = "positive") String sentiment,
    @Schema(description = "감성 분석 정확도", example = "88.24") double sentimentProbability,
    @Schema(description = "리뷰 이미지 URL", example = "http://tutti.com") String reviewImageUrls,
    @Schema(description = "리뷰 작성일", example = "2021-08-01") String createdAt,
    @Schema(description = "좋아요 개수", example = "0") int likeCount
) {

    public static ReviewDetailResponse from(Review review) {
        return new ReviewDetailResponse(
            review.getId(),
            review.getProductId(),
            review.getNickname(),
            review.getRating(),
            review.getContent(),
            review.getSentiment(),
            review.getSentimentProbability(),
            review.getReviewImageUrls(),
            review.getCreatedAt().toString(),
            review.getLikeCount()
        );
    }
}
