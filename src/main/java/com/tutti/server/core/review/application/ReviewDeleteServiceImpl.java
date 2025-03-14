package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewDeleteRequest;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewDeleteServiceImpl implements ReviewDeleteService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDeleteResponse deleteReview(ReviewDeleteRequest request)
        throws IllegalArgumentException {
        // 리뷰 조회
        Review review = reviewRepository.findOne(request.reviewId());

        // 리뷰 작성자 확인
        if (!review.getNickname().equals(request.userEmail())) {
            throw new IllegalArgumentException("리뷰를 삭제할 수 없습니다.");
        }

        // 리뷰 삭제
        reviewRepository.delete(review);

        // 삭제 성공 시, 응답 반환
        return new ReviewDeleteResponse(request.reviewId(), "리뷰가 삭제되었습니다.");
    }
}
