package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "리뷰 정보")
public record ReviewResponse(
    @Schema(description = "리뷰 ID", example = "1") Long id,
    @Schema(description = "리뷰 내용", example = "상품이 좋아요!") String content,  // 리뷰 내용
    @Schema(description = "평점", example = "5") Integer rating,  // 평점
    @Schema(description = "좋아요 수", example = "100") Long likeCount,  // 좋아요 수
    @Schema(description = "리뷰 작성일", example = "2022-01-01T10:00:00") LocalDateTime createdAt
    // 리뷰 작성일
) {

}

