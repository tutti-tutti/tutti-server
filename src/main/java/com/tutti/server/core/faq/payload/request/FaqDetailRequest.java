package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "FAQ 상세 조회 요청")
public record FaqDetailRequest(
    @NotNull(message = "FAQ ID는 필수입니다.")
    @Schema(description = "조회할 FAQ ID", example = "1")
    Long faqId
) {

}
