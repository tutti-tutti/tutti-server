package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewDeleteRequest;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;

public interface ReviewDeleteService {

    ReviewDeleteResponse deleteReview(ReviewDeleteRequest request) throws IllegalArgumentException;
}
