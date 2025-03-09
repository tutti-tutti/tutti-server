package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqFeedbackResponse {

    @Schema(description = "응답 메시지", example = "피드백이 반영되었습니다.")
    private String message;

    @Schema(description = "긍정 피드백 개수", example = "30")
    private long positive;

    @Schema(description = "부정 피드백 개수", example = "2")
    private long negative;
}
