package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;

public interface ReviewDeleteService {

    ReviewDeleteResponse deleteMyReview(Long reviewId);
}
