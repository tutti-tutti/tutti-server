package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FaqRequest(
    @Schema(description = "카테고리 ID", example = "1")
    @NotNull
    Long categoryId,
    @Schema(description = "질문", example = "반품 신청은 어떻게 하나요?")
    @NotBlank
    String question,
    @Schema(description = "답변", example = "마이페이지에서 반품 신청을 할 수 있습니다.")
    String answer,
    @Schema(description = "공개 여부", example = "true")
    Boolean isView
) {

}
