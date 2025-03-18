package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "감성 분석 머신러닝 모델 연동 API")
public interface SentimentApiSpec {

    @Operation(
        summary = "FastAPI 감성 분석 요청",
        description = "리뷰 내용을 감성 분석 머신러닝 모델을 통해 긍정 또는 부정으로 분류하고 정확도를 반환합니다."
    )
    SentimentResponse analyzeSentiment(SentimentRequest request);

}
