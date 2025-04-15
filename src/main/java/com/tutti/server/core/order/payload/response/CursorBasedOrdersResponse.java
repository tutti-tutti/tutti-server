package com.tutti.server.core.order.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "복합 커서 기반 주문 목록 응답 DTO")
public record CursorBasedOrdersResponse(

        @Schema(description = "주문 목록")
        List<OrderResponse> content,

        @Schema(description = "다음 요청에 사용할 커서(기준) 1: createdAt", example = "2025-03-29T15:45:37")
        LocalDateTime nextCursorCreatedAt,

        @Schema(description = "다음 요청에 사용할 커서(기준) 2: id", example = "4")
        Long nextCursorId,

        @Schema(description = "다음 페이지 존재 여부(true/false)", example = "true")
        boolean hasNext
) {

}
