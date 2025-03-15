package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

    private final ReviewRepository reviewRepository;

<<<<<<< HEAD
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {

        String reviewImagesString = String.join(",", reviewCreateRequest.reviewImages());

        Review review = Review.builder()
            .productId(reviewCreateRequest.productId())
            .nickname(reviewCreateRequest.nickname())
            .rating(reviewCreateRequest.rating())
            .content(reviewCreateRequest.content())
            .reviewImageUrls(reviewImagesString)
            .build();

=======
    public String getNicknameByMemberId(Long memberId) {
        if (memberId == 1L) {
            return "testUser";
        }
        return "defaultUser";
    }

    @Override
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        String username = "testUser";

        Long memberId = getMemberIdFromUsername(username);

        String nickname = getNicknameByMemberId(memberId);

        List<String> reviewImages = reviewCreateRequest.reviewImages();

        Review review = Review.createReview(
            reviewCreateRequest.orderItemId(),
            memberId,
            reviewCreateRequest.orderItemId(),
            reviewCreateRequest.rating(),
            reviewCreateRequest.content(),
            reviewImages,
            nickname
        );

>>>>>>> bde671b548ffdf548ba19939efb514a77f589427
        reviewRepository.save(review);

        return new ReviewCreateResponse("리뷰가 등록되었습니다.");
    }

    private Long getMemberIdFromUsername(String username) {
        if ("testUser".equals(username)) {
            return 1L;
        }
        return 999L;
    }
}
