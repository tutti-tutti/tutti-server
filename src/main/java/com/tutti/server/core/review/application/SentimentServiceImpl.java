package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SentimentServiceImpl implements SentimentService {

    private final RestClient sentimentRestClient;

    public SentimentServiceImpl() {
        this.sentimentRestClient = RestClient.builder()
            .baseUrl("http://3.37.129.164:8000")
            .build();
    }

    @Override
    public SentimentResponse analyzeSentiment(SentimentRequest request) {
        return sentimentRestClient.post()
            .uri("/analyze-sentiment/")
            .body(request)
            .retrieve()
            .body(SentimentResponse.class);
    }

    @Override
    public SentimentFeedbackResponse sendFeedback(SentimentFeedbackRequest request) {
        return sentimentRestClient.post()
            .uri("/feedback/")
            .body(request)
            .retrieve()
            .body(SentimentFeedbackResponse.class);
    }
}
