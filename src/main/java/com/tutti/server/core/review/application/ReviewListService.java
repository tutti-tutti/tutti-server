package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewListResponse;

public interface ReviewListService {

    ReviewListResponse getReviewsByProductId(Long productId, Long cursor, int size, String sort);
}


