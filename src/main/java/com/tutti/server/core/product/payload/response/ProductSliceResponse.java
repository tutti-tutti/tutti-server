package com.tutti.server.core.product.payload.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ProductSliceResponse(
        boolean hasNext,        // 다음 페이지 존재 여부
        Long nextCursorId,        // 다음 페이지 요청 시 사용할 커서 (마지막 상품 ID)
        int contentSize,        // 실제 반환된 데이터 개수
        List<ProductResponse> content
) {

    public static ProductSliceResponse fromEntity(boolean hasNext, Long nextCursorId,
            int contentSize,
            List<ProductResponse> productResponses) {
        return ProductSliceResponse.builder()
                .hasNext(hasNext)
                .nextCursorId(nextCursorId)
                .contentSize(contentSize)
                .content(productResponses)
                .build();
    }
}
