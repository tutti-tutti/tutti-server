package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 피드백 응답")
public record FaqFeedbackResponse(
    @Schema(description = "긍정적인 평가 수", example = "10")
    int positiveCount,

    @Schema(description = "부정적인 평가 수", example = "2")
    int negativeCount
) {

}
