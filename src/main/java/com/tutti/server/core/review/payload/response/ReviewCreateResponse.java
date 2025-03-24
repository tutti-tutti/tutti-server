package com.tutti.server.core.review.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutti.server.core.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 작성 응답")
public record ReviewCreateResponse(
        @Schema(description = "리뷰 ID", example = "1") long id,
        @Schema(description = "상품 ID", example = "1") long productId,
        @Schema(description = "상품 옵션별 ID", example = "1") long productItemId,
        @Schema(description = "유저 닉네임", example = "tutti") String nickname,
        @Schema(description = "평점", example = "5") float rating,
        @Schema(description = "리뷰 내용", example = "너무 좋아요!") String content,
        @Schema(description = "리뷰 이미지 URL", example = "http://tutti.com") String reviewImageUrls,
        @Schema(description = "감성 분석 결과", example = "positive") String sentiment,
        @Schema(description = "감성 분석 정확도", example = "88.24") double sentimentProbability,
        @Schema(description = "리뷰 작성일", example = "2021-08-01")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdAt
) {

    public static ReviewCreateResponse from(Review review) {
        var productItem = review.getProductItem();

        return ReviewCreateResponse.builder()
                .id(review.getId())
                .productId(productItem.getProduct().getId())
                .productItemId(productItem.getId())
                .nickname(review.getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .reviewImageUrls(review.getReviewImageUrls())
                .sentiment(review.getSentiment())
                .sentimentProbability(review.getSentimentProbability())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
