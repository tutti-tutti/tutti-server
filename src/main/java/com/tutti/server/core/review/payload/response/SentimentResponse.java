package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감정 분석 응답")
public record SentimentResponse(
    @Schema(description = "리뷰 내용", example = "좋아요")
    String review_text,
    @Schema(description = "긍정 or 부정", example = "positive")
    String sentiment,
    @Schema(description = "긍정 확률", example = "0.8")
    Double probability
) {

}
