package com.tutti.server.core.review.application;

import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.LatestReviewListResponse;
import com.tutti.server.core.review.payload.response.LikeReviewListResponse;
import com.tutti.server.core.review.payload.response.RatingReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import com.tutti.server.core.review.payload.response.SentimentPositiveAvgResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewCreateService reviewCreateService;
    private final ReviewLatestListService reviewLatestListService;
    private final ReviewRatingListService reviewRatingListService;
    private final ReviewLikeListService reviewLikeListService;
    private final ReviewDetailService reviewDetailService;
    private final ReviewMyListService reviewMyListService;
    private final ReviewDeleteService reviewDeleteService;
    private final ReviewLikeService reviewLikeService;
    private final ReviewRatingService reviewRatingService;
    private final ReviewCountStarService reviewCountStarService;
    private final SentimentPositiveService sentimentPositiveService;

    public void createReview(ReviewCreateRequest request, Long memberId) {
        reviewCreateService.createReview(request, memberId);
    }

    public LatestReviewListResponse getLatestReviews(Long productId, LatestReviewCursor cursor,
            Integer size) {
        return reviewLatestListService.getLatestReviews(productId, cursor, size);
    }

    public RatingReviewListResponse getRatingReviews(Long productId, RatingReviewCursor cursor,
            int size) {
        return reviewRatingListService.getRatingReviews(productId, cursor, size);
    }

    public LikeReviewListResponse getLikeReviews(Long productId, LikeReviewCursor cursor,
            int size) {
        return reviewLikeListService.getLikeReviews(productId, cursor, size);
    }

    public ReviewDetailResponse getReviewDetail(long reviewId, Long memberId) {
        return reviewDetailService.getReviewDetail(reviewId, memberId);
    }

    public ReviewMyListResponse getMyReviewList(Long memberId, Long cursor, int size) {
        return reviewMyListService.getMyList(memberId, cursor, size);
    }

    public ReviewDeleteResponse deleteReview(Long reviewId) {
        return reviewDeleteService.deleteMyReview(reviewId);
    }

    public ReviewLikeResponse likeReview(Long reviewId, Long memberId) {
        return reviewLikeService.likeReview(reviewId, memberId);
    }

    public ReviewRatingResponse reviewRating(Long productId) {
        return reviewRatingService.ratingAverage(productId);
    }

    public ReviewCountPerStarResponse reviewCountPerStar(Long productId) {
        return reviewCountStarService.reviewCountPerStar(productId);
    }

    public SentimentPositiveAvgResponse reviewSentimentPositiveAvg(Long productId) {
        return sentimentPositiveService.sentimentPositiveAvg(productId);
    }
}
