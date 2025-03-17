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

    @Schema(description = "상품 ID", example = "1")
    long productId,

    @Schema(description = "리뷰 작성자 이메일", example = "boseok.lee@hotmail.com")
    String email,

    @Schema(description = "리뷰 작성자 닉네임", example = "tutti")
    String nickname,

    @Schema(description = "리뷰 내용", example = "배송이 빠르고 좋아요!")
    String content,

    @Schema(description = "평점", example = "5")
    int rating,

    @Schema(description = "리뷰 이미지 URL 목록")
    List<String> reviewImageUrls,

    @Schema(description = "좋아요 개수", example = "15")
    int likeCount,

    @Schema(description = "리뷰 감성 분석 결과", example = "positive")
    String sentiment,

    @Schema(description = "리뷰 감성 분석 정확도(%)", example = "95.2")
    double sentimentProbability,

    @Schema(description = "리뷰 작성일시", example = "2024-03-16T12:00:00")
    LocalDateTime createdAt
) {

    public static ReviewResponse from(Review review) {
        List<String> imageUrls = review.getReviewImageUrls() == null ?
            List.of() : Arrays.asList(review.getReviewImageUrls().split(","));

        return new ReviewResponse(
            review.getId(),
            review.getProductId(),
            review.getNickname(),
            review.getContent(),
            review.getAuthorEmail(),
            review.getRating(),
            imageUrls,
            review.getLikeCount(),
            review.getSentiment(),
            review.getSentimentProbability(),
            review.getCreatedAt()
        );
    }
}
