package com.tutti.server.core.review.payload.response;

import com.tutti.server.core.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Schema(description = "리뷰 응답 객체")
public record ReviewResponse(
    @Schema(description = "리뷰 ID", example = "1") Long reviewId,
    @Schema(description = "작성자 닉네임", example = "JohnDoe") String nickname,
    @Schema(description = "평점", example = "5") Integer rating,
    @Schema(description = "리뷰 내용", example = "이 제품 정말 좋아요!") String content,
    @Schema(description = "리뷰 이미지 URL 목록") List<String> reviewImageUrls,
    @Schema(description = "좋아요 개수", example = "15") int likeCount,
    @Schema(description = "작성일", example = "2024-03-16T12:00:00") LocalDateTime createdAt
) {

    public static ReviewResponse from(Review review) {
        List<String> imageUrls = review.getReviewImageUrls() == null ?
            List.of() : Arrays.asList(review.getReviewImageUrls().split(","));
        return new ReviewResponse(
            review.getId(),
            review.getNickname(),
            review.getRating(),
            review.getContent(),
            imageUrls,
            review.getLikeCount(),
            review.getCreatedAt()
        );
    }
}
