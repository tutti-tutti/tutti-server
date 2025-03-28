package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Schema(description = "리뷰 응답 객체")
public record ReviewResponse(

        @Schema(description = "리뷰 ID", example = "1")
        long id,

        @Schema(description = "상품 옵션 ID", example = "1")
        Long productItemId,

        @Schema(description = "리뷰 작성자 닉네임", example = "tutti")
        String nickname,

        @Schema(description = "리뷰 내용", example = "배송이 빠르고 좋아요!")
        String content,

        @Schema(description = "평점", example = "4.5")
        float rating,

        @Schema(description = "리뷰 이미지 URL 목록")
        List<String> reviewImageUrls,

        @Schema(description = "좋아요 개수", example = "15")
        int likeCount,

        @Schema(description = "리뷰 감성 분석 결과", example = "positive")
        String sentiment,

        @Schema(description = "리뷰 감성 분석 정확도(%)", example = "95.2")
        double sentimentProbability,

        @Schema(description = "리뷰 작성일시", example = "2024-03-16T12:00:00")
        LocalDateTime createdAt,

        @Schema(description = "현재 로그인한 사용자의 좋아요 여부 (비로그인 시 false)", example = "false")
        boolean liked
) {

    // 오버로딩
    public static ReviewResponse from(Review review) {
        return from(review, false);
    }

    // 오버로딩
    public static ReviewResponse from(Review review, boolean liked) {
        List<String> imageUrls = review.getReviewImageUrls() == null ?
                List.of() : Arrays.asList(review.getReviewImageUrls().split(","));

        return new ReviewResponse(
                review.getId(),
                review.getProductItem().getId(),
                review.getNickname(),
                review.getContent(),
                review.getRating(),
                imageUrls,
                review.getLikeCount(),
                review.getSentiment(),
                review.getSentimentProbability(),
                review.getCreatedAt(),
                liked
        );
    }
}
