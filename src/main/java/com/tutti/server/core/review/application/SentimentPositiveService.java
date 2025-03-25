package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.SentimentPositiveAvgResponse;

public interface SentimentPositiveService {

    SentimentPositiveAvgResponse sentimentPositiveAvg(Long productId);
}
