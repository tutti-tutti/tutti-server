package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감성 분석 요청 파라미터")
public record SentimentRequest(

    @Schema(
        description = "리뷰 텍스트 (분석할 문장)",
        example = "배송이 늦어서 별로였지만 제품은 만족합니다."
    )
    String review_text
) {

}
