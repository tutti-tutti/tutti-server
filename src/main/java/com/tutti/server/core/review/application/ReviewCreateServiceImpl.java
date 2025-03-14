package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

    private final ReviewRepository reviewRepository;

    // nickname을 memberId로 찾는 메서드 (하드코딩)
    public String getNicknameByMemberId(Long memberId) {
        // 실제 환경에서는 DB에서 가져오도록 처리해야 합니다.
        if (memberId == 1L) {
            return "testUser"; // 예시: memberId가 1이면 "testUser" 반환
        }
        return "defaultUser";  // 기본 사용자명
    }

    @Override
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        // username을 고정된 값으로 하드코딩 (실제 환경에서는 @AuthenticationPrincipal 등을 통해 받아야 함)
        String username = "testUser";  // 예시로 고정된 username을 사용

        // username을 통해 memberId를 하드코딩 (예시로 1L을 사용)
        Long memberId = getMemberIdFromUsername(username);

        // memberId를 통해 nickname을 가져옴 (하드코딩된 값 사용)
        String nickname = getNicknameByMemberId(memberId);  // nickname을 가져옴

        // 리뷰 이미지 URL 목록을 List<String>으로 받음
        List<String> reviewImages = reviewCreateRequest.reviewImages();

        // 리뷰 객체 생성
        Review review = Review.createReview(
            reviewCreateRequest.orderItemId(),
            memberId,
            reviewCreateRequest.orderItemId(),  // 동일한 orderItemId를 두 번 사용하는 것은 확인 필요
            reviewCreateRequest.rating(),
            reviewCreateRequest.content(),
            reviewImages,  // List<String>으로 이미지를 전달
            nickname  // nickname을 review에 전달
        );

        // 리뷰 저장
        reviewRepository.save(review);

        return new ReviewCreateResponse("리뷰 작성이 완료되었습니다.");
    }

    // username을 통해 memberId를 하드코딩 (예시로 1L을 사용)
    private Long getMemberIdFromUsername(String username) {
        if ("testUser".equals(username)) {
            return 1L;  // 예시: username이 "testUser"일 때 memberId는 1L
        }
        return 999L;  // 기본 값 (사용자 존재하지 않으면 999L 반환)
    }
}
