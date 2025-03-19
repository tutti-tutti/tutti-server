package com.tutti.server.core.review.api;

import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;
    private final ReviewService reviewService;

    @Override
    @PostMapping
    public ResponseEntity<String> createReview(
        @RequestBody ReviewCreateRequest reviewCreateRequest) {
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

    @Override
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(
        @PathVariable("reviewId") long reviewId) {
        ReviewDetailResponse response = reviewService.getReviewDetail(reviewId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/myReviews")
    public ResponseEntity<ReviewMyListResponse> getMyReviewList(
        @RequestParam String nickname,
        @Parameter(description = "이전 페이지 마지막 ID (첫 요청 시 null 가능), empty value check!", allowEmptyValue = true)
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "20") int size
    ) {
        ReviewMyListResponse response = reviewService.getMyReviewList(nickname, cursor, size);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ReviewListResponse> getReviewsByProductId(
        @PathVariable Long productId,
        @Parameter(description = "이전 페이지 마지막 ID (첫 요청 시 null 가능), empty value check!", allowEmptyValue = true)
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "20") int size
    ) {
        ReviewListResponse response = reviewService.getReviewsByProduct(productId, cursor, size);
        return ResponseEntity.ok(response);
    }
}
