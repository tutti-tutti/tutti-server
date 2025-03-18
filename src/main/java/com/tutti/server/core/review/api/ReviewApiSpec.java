package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "리뷰 문서")
public interface ReviewApiSpec {

    @Operation(summary = "리뷰 작성 API")
    ResponseEntity<String> createReview(
        ReviewCreateRequest reviewCreateRequest);

//    리뷰는 상품에 달려야하니 상품 ID가 첫 엔드포인트가 되어야할듯? 가영님과 논의 필요 일단 구현은 완료. 주석처리
//    @Operation(summary = "리뷰 목록 조회 API")
//    ResponseEntity<ReviewListResponse> getReviewList(
//        @RequestBody ReviewListRequest reviewListRequest);

    @Operation(summary = "리뷰 상세 조회 API")
    ResponseEntity<ReviewDetailResponse> getReviewDetail(long reviewId);

    @Operation(summary = "개인 리뷰 목록 조회 API")
    ResponseEntity<ReviewMyListResponse> getMyReviewList(String nickname, Long cursor, int size);
}
