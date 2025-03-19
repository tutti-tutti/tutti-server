package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewMyListServiceImpl implements ReviewMyListService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewMyListResponse getMyList(String nickname, Long cursor, int size) {
        // 페이지네이션을 위한 PageRequest 객체 생성 (size 개수만큼 데이터 조회)
        final PageRequest pageRequest = PageRequest.of(0, size);

        // 커서(cursor)가 존재하면 이후 데이터를 조회, 없으면 처음 데이터를 조회
        final List<Review> reviews = Optional.ofNullable(cursor)
            // cursor가 null이 아니면 → findNextMyReviews() 실행
            .map(c -> reviewRepository.findNextMyReviews(nickname, c, pageRequest))
            // cursor가 null이면 → findFirstMyReviews() 실행
            .orElseGet(() -> reviewRepository.findFirstMyReviews(nickname, pageRequest));

        // 가져온 리뷰 리스트에서 마지막 리뷰의 ID를 nextCursor로 사용
        Long nextCursor = reviews.stream()
            .reduce((first, second) -> second) // 리스트의 마지막 요소 찾기
            .map(Review::getId) // 마지막 요소의 ID 추출
            .orElse(null); // 리스트가 비어 있으면 null 반환

        //  Review 엔티티 리스트를 ReviewResponse 리스트로 변환
        List<ReviewResponse> reviewResponses = reviews.stream()
            .map(ReviewResponse::from) // Review 엔티티 → ReviewResponse 변환
            .toList(); // Java 17 이상에서 사용 가능 (Collectors.toList() 대체)

        // Review 목록과 nextCursor를 담아 응답 반환
        return new ReviewMyListResponse(reviewResponses, nextCursor);
    }
}
