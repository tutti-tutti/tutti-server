package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewMyListServiceImpl implements ReviewMyListService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewMyListResponse getMyList(String nickname, Long cursor, int size) {
        List<Review> reviews;

        if (cursor == null) {
            reviews = reviewRepository.findFirstMyReviews(nickname, PageRequest.of(0, size));
        } else {
            reviews = reviewRepository.findNextMyReviews(nickname, cursor, PageRequest.of(0, size));
        }

        Long nextCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

        List<ReviewResponse> reviewResponses = reviews.stream()
            .map(ReviewResponse::from)
            .toList();

        return new ReviewMyListResponse(reviewResponses, nextCursor);
    }
}
