package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 목록 조회 요청")
public record FaqListRequest(
    @Schema(description = "검색어 (질문 키워드)", example = "환불") String query,
    @Schema(description = "카테고리명", example = "배송") String category,
    @Schema(description = "서브카테고리명", example = "국제 배송") String subcategory,
    @Schema(description = "페이지 번호 (기본값: 1)", example = "1") int page,
    @Schema(description = "페이지당 데이터 개수 (기본값: 10)", example = "10") int size
) {

    // 페이지 기본값 설정
    public FaqListRequest {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
    }
}
