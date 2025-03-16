package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "FAQ 수정 요청")
public record FaqUpdateRequest(

    @NotNull(message = "FAQ 카테고리 ID를 입력해주세요.")
    @Schema(description = "FAQ 카테고리")
    long categoryId,

    @NotNull(message = "FAQ ID를 입력해주세요.")
    @Schema(description = "FAQ ID", example = "1")
    long faqId,

    @NotNull(message = "FAQ 질문을 입력해주세요.")
    @Schema(description = "FAQ 질문", example = "배송 기간은 얼마나 걸리나요?")
    String question,

    @NotNull(message = "FAQ 답변을 입력해주세요.")
    @Schema(description = "FAQ 답변", example = "일반적으로 3~5일이 소요됩니다.")
    String answer,

    @NotNull(message = "FAQ 노출 여부를 입력해주세요.")
    @Schema(description = "FAQ 노출 여부", example = "true")
    boolean isView

) {

}
