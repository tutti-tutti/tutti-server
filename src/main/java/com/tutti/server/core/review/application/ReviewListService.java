package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewListResponse;

public interface ReviewListService {

    ReviewListResponse getReviewsByProductId(Long ProductId, Long cursor, int size, String sotr);
}


