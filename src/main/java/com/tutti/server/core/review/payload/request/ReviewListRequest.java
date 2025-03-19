package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 목록 요청 파라미터")
public record ReviewListRequest(
    @NotNull @Schema(description = "상품 ID", example = "1") long productId,
    @Min(1) @Max(100) @Schema(description = "요청할 리뷰 개수 (기본값: 20)", example = "20") Integer size,
    @Schema(description = "정렬 기준 (기본값: rating_desc)", example = "rating_desc") String sort,
    @Schema(description = "다음 페이지 조회를 위한 커서 값", example = "0") String nextCursor
) {

    public ReviewListRequest(long productId, Integer size, String sort, String nextCursor) {
        this.productId = productId;
        this.size = (size == null) ? 20 : size;
        this.sort = (sort == null) ? "rating_desc" : sort;
        this.nextCursor = nextCursor;
    }
}
