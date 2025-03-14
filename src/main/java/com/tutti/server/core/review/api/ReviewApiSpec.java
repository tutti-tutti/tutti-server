package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "리뷰 문서")
public interface ReviewApiSpec {

    @Operation(summary = "리뷰 작성 API")
    ResponseEntity<String> createReview(ReviewCreateRequest reviewCreateRequest);

    @Operation(summary = "리뷰 목록 조회 API")
    ResponseEntity<ReviewListResponse> getReviewList(ReviewListRequest reviewListRequest);

    @Operation(summary = "리뷰 삭제 API")
    ResponseEntity<ReviewDeleteResponse> deleteReview(Long reviewId, String userEmail);

}
