package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 수정 응답")
public record FaqUpdateResponse(

    @Schema(description = "FAQ ID", example = "1")
    Long faqId,

    @Schema(description = "FAQ 질문", example = "배송 기간은 얼마나 걸리나요?")
    String question,

    @Schema(description = "FAQ 답변", example = "일반적으로 3~5일이 소요됩니다.")
    String answer,

    @Schema(description = "FAQ 노출 여부", example = "true")
    Boolean isView,

    @Schema(description = "카테고리 이름", example = "배송 관련 > 국내 배송")
    String categoryName
) {

}
