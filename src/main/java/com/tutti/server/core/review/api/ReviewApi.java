package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.product.application.ProductServiceImpl;
import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.application.SentimentService;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final SentimentService sentimentService;
    private final ProductServiceImpl productService;

    @Override
    @PostMapping("/analyze-sentiment")
    public SentimentResponse analyzeSentiment(@RequestBody SentimentRequest request) {
        return sentimentService.analyzeSentiment(request);
    }

    @Override
    @PostMapping("/feedback")
    public SentimentFeedbackResponse sendFeedback(@RequestBody SentimentFeedbackRequest request) {
        return sentimentService.sendFeedback(request);
    }

    @Override
    @PostMapping
    public ResponseEntity<String> createReview(
        @RequestBody ReviewCreateRequest reviewCreateRequest,
        @AuthenticationPrincipal CustomUserDetails user) {
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
    @GetMapping("/product/{productId}")
    public ResponseEntity<ReviewListResponse> getReviewList(
        @PathVariable Long productId,
        @Parameter(description = "이전 페이지 마지막 ID (첫 요청 시 null 가능), empty value check!", allowEmptyValue = true)
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "rating_desc") String sort
    ) {
        ReviewListResponse response = reviewService.getReviews(productId, cursor, size, sort);
        return ResponseEntity.ok(response);
    }


    @Override
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(
        @PathVariable("reviewId") long reviewId,
        @AuthenticationPrincipal CustomUserDetails user) {
        Long memberId = (user != null) ? user.getMemberId() : null;
        ReviewDetailResponse response = reviewService.getReviewDetail(reviewId, memberId);
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
    @DeleteMapping("/myReviews/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteMyReview(
        @PathVariable Long reviewId
    ) {
        ReviewDeleteResponse response = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/{reviewId}/reviewLike")
    public ResponseEntity<ReviewLikeResponse> likeReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long memberId = user.getMemberId();
        ReviewLikeResponse response = reviewService.likeReview(reviewId, memberId);
        return ResponseEntity.ok(response);
    }
}
