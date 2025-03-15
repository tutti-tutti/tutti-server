package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "FAQ 생성 요청")
public record FaqCreateRequest(
    @NotNull(message = "카테고리 ID는 필수입니다.")
    @Schema(description = "FAQ 카테고리 ID", example = "1")
    Long categoryId,

    @NotNull(message = "질문은 필수입니다.")
    @Schema(description = "FAQ 질문", example = "상품 배송이 언제 이루어지나요?")
    String question,

    @NotNull(message = "답변은 필수입니다.")
    @Schema(description = "FAQ 답변", example = "상품은 3일 이내에 배송됩니다.")
    String answer,
    
    @NotNull(message = "보기 여부는 필수입니다.")
    @Schema(description = "FAQ 보이기 여부", example = "true")
    boolean isView
) {

    public FaqCreateRequest {
        if (categoryId == null || question == null || answer == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }
    }
}
