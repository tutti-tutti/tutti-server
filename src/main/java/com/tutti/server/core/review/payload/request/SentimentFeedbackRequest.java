package com.tutti.server.core.review.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감성 분석 피드백 요청")
public record SentimentFeedbackRequest(

    @JsonProperty("review_text")
    @Schema(description = "피드백 대상 리뷰 텍스트", example = "배송이 늦어서 별로였지만 제품은 만족합니다.")
    String reviewText,

    @Schema(description = "기존 감성 분석 결과", example = "negative")
    String sentiment,

    @Schema(description = "피드백 결과 (Correct or Incorrect)", example = "Incorrect")
    String feedback
) {

}
