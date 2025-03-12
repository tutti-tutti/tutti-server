package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FaqDetailRequest(
    @Schema(description = "조회할 FAQ ID", example = "1")
    Long faqId
) {

}
