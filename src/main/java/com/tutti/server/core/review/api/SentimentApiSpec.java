package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

@Tag(name = "감정 분석 API")
public interface SentimentApiSpec {

    @Operation(summary = "감정 분석", description = "리뷰의 감정을 분석한다.")
    @PostExchange("/analyze-sentiment/")
    SentimentResponse analyzeSentiment(@RequestBody SentimentRequest request);

}
