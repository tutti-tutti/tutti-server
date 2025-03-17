package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감정 분석 요청 파라미터")
public record SentimentRequest(
    @Schema(description = "리뷰 내용", example = "울 신랑 출근할때 쓰라고 사줬어요. 좋다고 하네요.")
    String review_text
) {

}
