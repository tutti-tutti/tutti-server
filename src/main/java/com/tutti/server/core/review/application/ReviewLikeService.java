package com.tutti.server.core.review.application;

public interface ReviewLikeService {

    // 리뷰 좋아요를 등록하는 메서드
    void likeReview(Long reviewId, Long userId);

    // 리뷰 좋아요를 취소하는 메서드
    void cancelLikeReview(Long reviewId, Long userId);

    // 리뷰 싫어요를 등록하는 메서드
    void dislikeReview(Long reviewId, Long userId);

    // 리뷰 싫어요를 취소하는 메서드
    void cancelDislikeReview(Long reviewId, Long userId);
}
