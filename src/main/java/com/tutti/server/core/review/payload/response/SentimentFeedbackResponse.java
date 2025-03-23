package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감성 분석 피드백 응답")
public record SentimentFeedbackResponse(

    @Schema(description = "서버 처리 메시지",
        example = "Feedback received, model updated and review saved.")
    String message
) {

}
