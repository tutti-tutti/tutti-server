package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "FAQ 피드백 응답")
public record FaqFeedbackResponse(
    @Schema(description = "긍정") int positiveCount,
    @Schema(description = "부정") int negativeCount) {

}
