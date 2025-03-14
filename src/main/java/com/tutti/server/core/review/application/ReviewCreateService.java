package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;

public interface ReviewCreateService {
    
    ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest);
}
