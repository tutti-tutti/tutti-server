package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 생성 요청")
public record FaqCreateRequest(
    Long categoryId,        // 카테고리 ID
    String question,        // 질문
    String answer,          // 답변
    boolean isView          // 표시 여부
) {

    public FaqCreateRequest {
        if (categoryId == null || question == null || answer == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }
    }
}
