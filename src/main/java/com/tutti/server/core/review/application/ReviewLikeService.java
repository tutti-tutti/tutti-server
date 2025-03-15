package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewLikeRequest;

public interface ReviewLikeService {

    void likeReview(ReviewLikeRequest request);

    void cancelLikeReview(ReviewLikeRequest request);

    void dislikeReview(ReviewLikeRequest request);

    void cancelDislikeReview(ReviewLikeRequest request);
}
