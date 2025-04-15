package com.tutti.server.core.product.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SearchRequest(
        @Schema(description = "검색어")
        String keyword,
        @Schema(description = "다음페이지 스크롤을 위해 기억야할 productId")
        Long cursorId,
        @Schema(description = "한 페이당 불러올 상품개수")
        int size
) {

}
