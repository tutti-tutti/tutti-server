package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.application.SentimentService;
import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.LatestReviewListResponse;
import com.tutti.server.core.review.payload.response.LikeReviewListResponse;
import com.tutti.server.core.review.payload.response.RatingReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentPositiveAvgResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private final ReviewService reviewService;
    private final SentimentService sentimentService;

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
            reviewService.createReview(reviewCreateRequest, user.getMemberId());
            return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


    @Override
    @GetMapping("/{productId}/latest")
    public ResponseEntity<LatestReviewListResponse> getLatestReviewList(
            @PathVariable Long productId,
            @ParameterObject @ModelAttribute LatestReviewCursor cursor,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long memberId = user.getMemberId();
        LatestReviewListResponse response = reviewService.getLatestReviews(productId, cursor, size,
                memberId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{productId}/rating")
    public ResponseEntity<RatingReviewListResponse> getRatingReviewList(
            @PathVariable Long productId,
            @ParameterObject @ModelAttribute RatingReviewCursor cursor,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        Long memberId = user.getMemberId();
        RatingReviewListResponse response = reviewService.getRatingReviews(productId, cursor, size,
                memberId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{productId}/like")
    public ResponseEntity<LikeReviewListResponse> getLikeReviewList(
            @PathVariable Long productId,
            @ParameterObject @ModelAttribute LikeReviewCursor cursor,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        Long memberId = user.getMemberId();
        LikeReviewListResponse response = reviewService.getLikeReviews(productId, cursor, size,
                memberId);
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
            @AuthenticationPrincipal CustomUserDetails user,
            @Parameter(description = "이전 페이지 마지막 ID (첫 요청 시 null 가능), empty value check!", allowEmptyValue = true)
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        Long memberId = user.getMemberId();
        ReviewMyListResponse response = reviewService.getMyReviewList(memberId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/myReviews/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteMyReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long memberId = user.getMemberId();
        ReviewDeleteResponse response = reviewService.deleteReview(reviewId, memberId);
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

    @Override
    @GetMapping("/{productId}/average")
    public ResponseEntity<ReviewRatingResponse> ratingAverage(
            @PathVariable Long productId
    ) {
        ReviewRatingResponse response = reviewService.reviewRating(productId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{productId}/countStar")
    public ResponseEntity<ReviewCountPerStarResponse> countStar(
            @PathVariable Long productId
    ) {
        ReviewCountPerStarResponse response = reviewService.reviewCountPerStar(productId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{productId}/positiv")
    public ResponseEntity<SentimentPositiveAvgResponse> senPositive(
            @PathVariable Long productId) {
        SentimentPositiveAvgResponse response = reviewService.reviewSentimentPositiveAvg(productId);
        return ResponseEntity.ok(response);
    }
}
