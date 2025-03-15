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

    @Operation(summary = "리뷰 작성 API", description = "사용자가 상품에 대한 리뷰를 작성합니다.")
    ResponseEntity<String> createReview(ReviewCreateRequest reviewCreateRequest);

    @Operation(summary = "리뷰 목록 조회 API", description = "특정 상품에 대한 리뷰 목록을 조회합니다. 무한 스크롤 방식 및 정렬 기준을 지원합니다.")
    ResponseEntity<ReviewListResponse> getReviewList(ReviewListRequest reviewListRequest);

    @Operation(summary = "리뷰 삭제 API", description = "사용자가 자신이 작성한 리뷰를 삭제합니다.")
    ResponseEntity<ReviewDeleteResponse> deleteReview(Long reviewId, String userEmail);

    @Operation(summary = "상품 리뷰 초기 10개 조회 API", description = "상품 상세 페이지에서 초기 10개의 리뷰를 조회합니다. 최신순으로 정렬됩니다.")
    ResponseEntity<ReviewListResponse> getInitialReviewList(Long productId, Integer size,
        String sort);

    @Operation(summary = "상품 리뷰 무한 스크롤 조회 API", description = "상품에 대한 리뷰를 무한 스크롤 방식으로 조회합니다. 페이지네이션을 지원합니다.")
    ResponseEntity<ReviewListResponse> getReviewsWithPagination(Long productId, Integer size,
        String sort, String nextCursor);

    @Operation(summary = "리뷰 좋아요 추가 API", description = "사용자가 특정 리뷰에 좋아요를 추가합니다.")
    ResponseEntity<String> likeReview(Long reviewId, String userEmail);
//
//    @Operation(summary = "리뷰 좋아요 취소 API", description = "사용자가 특정 리뷰의 좋아요를 취소합니다.")
//    ResponseEntity<String> unlikeReview(Long reviewId, String userEmail);
}
