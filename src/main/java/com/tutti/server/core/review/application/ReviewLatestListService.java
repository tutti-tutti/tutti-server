package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import com.tutti.server.core.review.payload.response.LatestReviewListResponse;

public interface ReviewLatestListService {

    LatestReviewListResponse getLatestReviews(Long productId, LatestReviewCursor cursor,
            int size);
}
