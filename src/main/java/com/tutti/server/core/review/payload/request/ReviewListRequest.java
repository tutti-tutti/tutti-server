package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 목록 요청 파라미터")
public record ReviewListRequest(
    @Schema(description = "상품 ID", example = "1") Long productId,
    @Schema(description = "요청할 리뷰 개수 (기본값: 20)", example = "20") Integer size,
    @Schema(description = "정렬 기준 (기본값: created_at_desc)", example = "created_at_desc") String sort,
    @Schema(description = "다음 페이지 조회를 위한 커서 값", example = "123") String nextCursor
) {

    public ReviewListRequest {
        if (size == null) {
            size = 20;
        }
        if (sort == null) {
            sort = "created_at_desc";
        }
    }
}
