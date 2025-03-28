package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import com.tutti.server.core.review.payload.response.RatingReviewListResponse;

public interface ReviewRatingListService {

    RatingReviewListResponse getRatingReviews(Long productId, RatingReviewCursor cursor, int size,
            Long memberId);
}
