package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewLikeResponse;

public interface ReviewLikeService {

    ReviewLikeResponse likeReview(Long reviewId, Long memberId);
}
