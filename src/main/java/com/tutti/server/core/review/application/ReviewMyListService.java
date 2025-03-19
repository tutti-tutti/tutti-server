package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewMyListService {

    ReviewMyListResponse getMyList(String email, Long cursor, int size);

    @Transactional(readOnly = true)
    ReviewMyListResponse getReviewsByProductId(Long productId, Long cursor, int size);
}
