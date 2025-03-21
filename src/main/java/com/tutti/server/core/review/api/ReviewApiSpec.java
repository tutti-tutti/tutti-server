package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "리뷰 API")
public interface ReviewApiSpec {

    @Operation(summary = "리뷰 작성 API")
    ResponseEntity<String> createReview(
        ReviewCreateRequest reviewCreateRequest);

    @Operation(summary = "리뷰 목록 조회 API")
    ResponseEntity<ReviewListResponse> getReviewList(Long productId, Long cursor, int size,
        String sort);

    @Operation(summary = "리뷰 상세 조회 API")
    ResponseEntity<ReviewDetailResponse> getReviewDetail(long reviewId);

    @Operation(summary = "개인 리뷰 목록 조회 API")
    ResponseEntity<ReviewMyListResponse> getMyReviewList(String nickname, Long cursor, int size);

    @Operation(summary = "리뷰 삭제 API")
    ResponseEntity<ReviewDeleteResponse> deleteMyReview(Long reviewId);
}
