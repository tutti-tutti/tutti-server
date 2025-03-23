package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;

public interface SentimentService {

    SentimentResponse analyzeSentiment(SentimentRequest request);

    SentimentFeedbackResponse sendFeedback(SentimentFeedbackRequest request);
}
