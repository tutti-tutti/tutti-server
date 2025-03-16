package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;

public interface ReviewListService {

    ReviewListResponse getReviews(ReviewListRequest request);

    ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor);

}


