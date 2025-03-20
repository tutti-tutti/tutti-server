package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.annotation.PostExchange;


@RestController
@RequiredArgsConstructor
public class SentimentApi implements SentimentApiSpec {

    private final RestClient restClient;

    public SentimentApi() {
        this.restClient = RestClient.builder()
            .baseUrl("http://3.37.129.164:8000")  // EC2 퍼블릭 IP
            .build();
    }

    @Override
    @PostExchange("/analyze-sentiment/")
    public SentimentResponse analyzeSentiment(@RequestBody SentimentRequest request) {
        return restClient.post()
            .uri("/analyze-sentiment/")
            .body(request)
            .retrieve()
            .body(SentimentResponse.class);
    }
}
