package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "FAQ 목록 응답")
public record FaqListResponse(
    @Schema(description = "총 결과 개수", example = "9") int totalResults,
    @Schema(description = "현재 페이지 번호", example = "1") int currentPage,
    @Schema(description = "페이지당 FAQ 개수", example = "10") int size,
    @Schema(description = "FAQ 목록") List<FaqResponse> results
) {

    public static FaqListResponse from(int totalResults, int currentPage, int size,
        List<FaqResponse> results) {
        return new FaqListResponse(totalResults, currentPage, size, results);
    }
}
