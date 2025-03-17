package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewListServiceImpl implements ReviewListService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewListResponse getReviews(ReviewListRequest request) {
        return getReviewsWithPagination(
            request.productId(),
            Optional.ofNullable(request.size()).orElse(20),
            request.sort(),
            request.nextCursor()
        );
    }

    @Override
    public ReviewListResponse getReviewsWithPagination(Long productId, Integer size, String sort,
        String nextCursor) {
        Long cursorId = (nextCursor != null && !nextCursor.isEmpty()) ? Long.parseLong(nextCursor)
            : Long.MAX_VALUE;

        Pageable pageable = PageRequest.of(0, size + 1, getSort(sort)); // size + 1 개 조회

        List<Review> reviews = reviewRepository.findReviewsByProductIdAndCursor(productId, cursorId,
            pageable);

        // nextCursor 설정: 마지막으로 가져온 리뷰의 ID 사용
        String nextCursorValue =
            (reviews.size() > size) ? String.valueOf(reviews.get(size).getId()) : null;

        // 응답 리스트: size만큼 잘라서 반환
        List<ReviewResponse> reviewResponses = reviews.stream()
            .limit(size)
            .map(ReviewResponse::from)
            .collect(Collectors.toList());

        return new ReviewListResponse(reviewResponses, nextCursorValue);
    }

    private Sort getSort(String sort) {
        return switch (sort.toLowerCase()) {
            case "rating_desc" -> Sort.by(Sort.Order.desc("rating"));
            case "like_desc" -> Sort.by(Sort.Order.desc("likeCount"));
            default -> Sort.by(Sort.Order.desc("createdAt")); // 기본값: 최신순
        };
    }
}
