package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;

public interface ReviewCountStarService {

    ReviewCountPerStarResponse reviewCountPerStar(Long productId);
}
