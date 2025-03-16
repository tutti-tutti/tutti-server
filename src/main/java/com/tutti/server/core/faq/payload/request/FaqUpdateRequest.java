package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 수정 요청")
public record FaqUpdateRequest(

    @Schema(description = "FAQ 카테고리")
    long categoryId,

    @Schema(description = "FAQ ID", example = "1")
    long faqId,

    @Schema(description = "FAQ 질문", example = "배송 기간은 얼마나 걸리나요?")
    String question,

    @Schema(description = "FAQ 답변", example = "일반적으로 3~5일이 소요됩니다.")
    String answer,

    @Schema(description = "FAQ 노출 여부", example = "true")
    boolean isView

) {

}
