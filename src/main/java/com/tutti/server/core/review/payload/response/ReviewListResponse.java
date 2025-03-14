package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 목록 응답")
public record ReviewListResponse(
    @Schema(description = "리뷰 목록", example = "[{\"id\": 1, \"content\": \"상품이 좋아요!\", \"rating\": 5, \"likeCount\": 100, \"createdAt\": \"2022-01-01T10:00:00\"}]")
    List<ReviewResponse> reviews,  // 리뷰 목록

    @Schema(description = "다음 페이지 조회를 위한 커서 값", example = "123456")
    String nextCursor  // 다음 페이지 조회를 위한 커서 값
) {

}
