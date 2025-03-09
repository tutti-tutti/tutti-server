package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FaqFeedbackRequest {

    @Schema(description = "피드백 유형 (true: 긍정, false: 부정)", example = "true")
    @NotNull
    private Boolean positive;
}
