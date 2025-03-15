package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "FAQ 피드백 요청")
public record FaqFeedbackRequest(
    @NotNull(message = "피드백은 필수입니다.")
    @Schema(description = "FAQ에 대한 피드백 (true: 긍정, false: 부정)", example = "true")
    boolean feedback
) {

}
