package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 상세 조회 요청")
public record FaqDetailRequest(
    @Schema(description = "조회할 FAQ ID", example = "1")
    Long faqId
) {

}
