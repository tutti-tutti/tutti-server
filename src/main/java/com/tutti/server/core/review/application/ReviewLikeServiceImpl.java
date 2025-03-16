package com.tutti.server.core.review.application;

import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.domain.ReviewLike;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewLikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImpl implements ReviewLikeService {

    private final MemberRepository memberRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository; // 리뷰 엔티티를 조회하기 위해


    @Override
    public void likeReview(ReviewLikeRequest request) {
        Long reviewId = request.reviewId();
        Long memberId = request.memberId();

        // 리뷰가 존재하는지 확인
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 사용자가 이미 해당 리뷰에 좋아요를 눌렀는지 확인
        ReviewLike existingLike = reviewLikeRepository.findByReviewIdAndMemberId(reviewId,
            memberId);
        if (existingLike != null) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        // 새로운 좋아요 기록 추가
        ReviewLike newLike = new ReviewLike(review, memberId);
        reviewLikeRepository.save(newLike);

        System.out.println("Like reviewId: " + reviewId + " for memberId: " + memberId);
    }

    @Override
    public void cancelLikeReview(ReviewLikeRequest request) {
        Long reviewId = request.reviewId();
        Long memberId = request.memberId();

        // 좋아요 기록 조회
        ReviewLike existingLike = reviewLikeRepository.findByReviewIdAndMemberId(reviewId,
            memberId);
        if (existingLike == null) {
            throw new IllegalArgumentException("좋아요를 취소할 수 없습니다. 해당 리뷰에 대한 좋아요가 없습니다.");
        }

        // 좋아요 기록 삭제
        reviewLikeRepository.delete(existingLike);

        System.out.println("Cancel like for reviewId: " + reviewId + " and memberId: " + memberId);
    }

    @Override
    public void dislikeReview(ReviewLikeRequest request) {
        Long reviewId = request.reviewId();
        Long memberId = request.memberId();

        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        ReviewLike existingDislike = reviewLikeRepository.findByReviewIdAndMemberId(reviewId,
            memberId);
        if (existingDislike != null && existingDislike.isLiked()) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다. 싫어요로 변경할 수 없습니다.");
        }

        ReviewLike newDislike = new ReviewLike(review, memberId, false);
        reviewLikeRepository.save(newDislike);

        System.out.println("Dislike reviewId: " + reviewId + " for memberId: " + memberId);
    }

    @Override
    public void cancelDislikeReview(ReviewLikeRequest request) {
        Long reviewId = request.reviewId();
        Long memberId = request.memberId();

        // 싫어요 기록 조회
        ReviewLike existingDislike = reviewLikeRepository.findByReviewIdAndMemberId(reviewId,
            memberId);
        if (existingDislike == null || existingDislike.isLiked()) {
            throw new IllegalArgumentException("싫어요를 취소할 수 없습니다. 해당 리뷰에 대한 싫어요가 없습니다.");
        }

        reviewLikeRepository.delete(existingDislike);

        System.out.println(
            "Cancel dislike for reviewId: " + reviewId + " and memberId: " + memberId);
    }
}

