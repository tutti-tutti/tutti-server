package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.response.ReviewMyListResponse;

public interface ReviewMyListService {

    ReviewMyListResponse getMyList(String email, Long cursor, int size);
}
