package com.tutti.server.core.review.payload.response;

public record SentimentResponse(
    String review_text,
    String sentiment,
    Double probability
) {

}
