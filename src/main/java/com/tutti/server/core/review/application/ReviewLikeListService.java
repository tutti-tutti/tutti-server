package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import com.tutti.server.core.review.payload.response.LikeReviewListResponse;

public interface ReviewLikeListService {

    LikeReviewListResponse getLikeReviews(Long productId, LikeReviewCursor cursor, int size);
}
