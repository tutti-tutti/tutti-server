package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감성 분석 응답")
public record SentimentResponse(

    @Schema(description = "입력한 리뷰", example = "배송이 늦어서 별로였지만 제품은 만족합니다.")
    String review_text,

    @Schema(description = "분석된 감성 결과", example = "negative")
    String sentiment,

    @Schema(description = "예측 확률 (0~100)", example = "82.7")
    double probability
) {

}

