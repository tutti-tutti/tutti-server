package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewMyListResponse;

public interface ReviewMyListService {

    ReviewMyListResponse getMyList(Long memberId, Long cursor, int size);
}
