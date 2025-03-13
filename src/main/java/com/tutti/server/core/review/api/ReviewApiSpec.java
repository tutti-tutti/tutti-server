package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import org.springframework.http.ResponseEntity;

public interface ReviewApiSpec {

    ResponseEntity<String> createReview(ReviewCreateRequest reviewCreateRequest);
}
