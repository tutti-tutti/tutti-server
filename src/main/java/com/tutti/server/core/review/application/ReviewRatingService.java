package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewRatingResponse;

public interface ReviewRatingService {

    ReviewRatingResponse ratingAverage(Long productId);
}
