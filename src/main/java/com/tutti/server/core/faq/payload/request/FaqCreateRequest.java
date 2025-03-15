package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 생성 요청")
public record FaqCreateRequest(
    Long categoryId,
    String question,
    String answer,
    boolean isView
) {

    public FaqCreateRequest {
        if (categoryId == null || question == null || answer == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }
    }
}
