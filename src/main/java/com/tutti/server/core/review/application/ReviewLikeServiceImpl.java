package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.domain.ReviewLike;
import com.tutti.server.core.review.infrastructure.ReviewLikeRepository;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImpl implements ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewLikeResponse likeReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findOne(reviewId);

        boolean liked = reviewLikeRepository.findByReviewAndMemberId(review, memberId)
            .map(existingLike -> {
                reviewLikeRepository.delete(existingLike);
                return false;
            })
            .orElseGet(() -> {
                reviewLikeRepository.save(new ReviewLike(review, memberId));
                return true;
            });

        return new ReviewLikeResponse(review.getId(), review.getLikeCount(), liked);
    }
}

