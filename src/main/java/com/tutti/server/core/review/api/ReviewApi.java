package com.tutti.server.core.review.api;

import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;
    private final ReviewService reviewService;

    @Override
    public ResponseEntity<String> createReview(ReviewCreateRequest reviewCreateRequest) {
        try {
            ReviewCreateResponse review = reviewCreateServiceImpl.createReview(reviewCreateRequest);
            return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    // 상품에 달린 리뷰 목록 엔드포인트 수정 필요?? 나중에 상품 쪽이랑 얘기 해봐야 할듯.
//    @Override
//    public ResponseEntity<ReviewListResponse> getReviewList(
//        ReviewListRequest reviewListRequest) {
//        ReviewListResponse response = reviewService.getReviews(reviewListRequest);
//        return ResponseEntity.ok(response);
//    }

    @Override
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(
        @PathVariable("reviewId") long reviewId) {
        ReviewDetailResponse response = reviewService.getReviewDetail(reviewId);
        return ResponseEntity.ok(response);
    }
}
