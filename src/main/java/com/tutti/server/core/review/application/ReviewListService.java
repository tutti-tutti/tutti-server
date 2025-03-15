package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;

public interface ReviewListService {

    // 상품의 리뷰를 조회하는 메서드
    ReviewListResponse getReviews(ReviewListRequest request);

    // 상품의 초기 리뷰 목록을 조회하는 메서드
    ReviewListResponse getInitialReviews(Long productId, Integer size, String sort);

    // 상품 리뷰를 무한 스크롤 방식으로 페이지네이션하여 조회하는 메서드
    ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor);
}
