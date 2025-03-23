package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewDetailResponse;

public interface ReviewDetailService {

    ReviewDetailResponse getReviewDetail(long reviewId, Long memberId);
}
