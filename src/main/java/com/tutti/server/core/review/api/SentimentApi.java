package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;


@RestController
@RequiredArgsConstructor
public class SentimentApi implements SentimentApiSpec {

    private final RestClient restClient;

    @Override
    public SentimentResponse analyzeSentiment(SentimentRequest request) {
        return restClient.post()
            .uri("/analyze-sentiment/")
            .body(request)
            .retrieve()
            .body(SentimentResponse.class);
    }
}
