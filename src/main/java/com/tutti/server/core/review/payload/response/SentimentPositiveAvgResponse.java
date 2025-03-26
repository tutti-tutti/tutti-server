package com.tutti.server.core.review.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "상품 감성분석 긍정 비율")
public record SentimentPositiveAvgResponse(
        @Schema(description = "상품 아이디", example = "49")
        Long productId,
        @Schema(description = "상품 리뷰의 긍정 비율", example = "80.5")
        double positivePer
) {

    public static SentimentPositiveAvgResponse of(long productId, double positivePer) {
        return SentimentPositiveAvgResponse.builder()
                .productId(productId)
                .positivePer(positivePer)
                .build();
    }
}
