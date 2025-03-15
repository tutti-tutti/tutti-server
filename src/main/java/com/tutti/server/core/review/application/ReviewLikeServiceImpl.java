package com.tutti.server.core.review.application;

import org.springframework.stereotype.Service;

@Service
public class ReviewLikeServiceImpl implements ReviewLikeService {

    @Override
    public void likeReview(Long reviewId, Long userEmail) {
        // 리뷰 좋아요 기능 구현
        // 예: DB에 리뷰와 사용자 정보를 바탕으로 좋아요 기록
    }

    @Override
    public void cancelLikeReview(Long reviewId, Long userId) {
        // 리뷰 좋아요 취소 기능 구현
        // 예: DB에서 해당 리뷰의 좋아요 기록 삭제
    }

    @Override
    public void dislikeReview(Long reviewId, Long userId) {
        // 리뷰 싫어요 기능 구현
        // 예: DB에 리뷰와 사용자 정보를 바탕으로 싫어요 기록
    }

    @Override
    public void cancelDislikeReview(Long reviewId, Long userId) {
        // 리뷰 싫어요 취소 기능 구현
        // 예: DB에서 해당 리뷰의 싫어요 기록 삭제
    }
}
