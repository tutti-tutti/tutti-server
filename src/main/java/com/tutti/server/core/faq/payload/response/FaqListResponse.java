package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 목록 응답")
public record FaqListResponse(
    @Schema(description = "총 결과 개수", example = "9") int totalFaq,
    @Schema(description = "현재 페이지 번호", example = "1") int currentPage,
    @Schema(description = "페이지당 FAQ 개수", example = "10") int size,
    @Schema(description = "검색된 FAQ 목록 응답") FaqSearchResponse faqResults // 명확한 이름으로 변경
) {

}
