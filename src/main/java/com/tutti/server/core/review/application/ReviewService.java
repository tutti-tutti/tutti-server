package com.tutti.server.core.review.application;

import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.infrastructure.ReviewRepository;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    final private ReviewRepository reviewRepository;

    // @Autowired
    // private OrderItemRepository orderItemRepository; // OrderItemRepository는 아직 구현되지 않음

    public Review createReview(String username, ReviewCreateRequest reviewCreateRequest) {
        // 유효한 orderItemId인지 확인
        // 주석 처리된 부분: 사용자가 실제로 해당 상품을 구매했는지 확인하는 로직은 OrderItemRepository가 구현된 후에 처리 예정
        // if (!isValidOrderItem(reviewRequestDto.orderItemId(), username)) {
        //     throw new IllegalArgumentException("해당 상품을 구매한 사용자만 리뷰를 작성할 수 있습니다.");
        // }

        // productId와 memberId는 username 또는 다른 정보를 바탕으로 추출해야 할 수 있습니다.
        Long productId = reviewCreateRequest.orderItemId(); // productId는 orderItemId에서 추출할 수 있습니다.
        Long memberId = getMemberIdFromUsername(username); // 예시: username에서 memberId를 찾는 로직 필요

        // 리뷰 객체 생성 (이미지는 고려하지 않음)
        Review review = Review.createReview(
            productId,
            memberId,
            reviewCreateRequest.orderItemId(),
            reviewCreateRequest.rating(),
            reviewCreateRequest.content(),
            new String[]{} // 이미지 처리는 제외
        );

        // 리뷰 저장
        return reviewRepository.save(review);
    }

    // 주문 아이템의 유효성 검증 (구매한 사용자만 리뷰 작성 가능)
    // 주석 처리된 부분: 주문 아이템 검증 로직은 OrderItemRepository가 구현되면 활성화할 예정
    // private boolean isValidOrderItem(Long orderItemId, String username) {
    //     Long memberId = getMemberIdFromUsername(username); // username에서 memberId를 찾는 로직

    //     // 해당 주문 아이템이 해당 사용자와 연결되어 있는지 확인
    //     Optional<OrderItem> orderItem = orderItemRepository.findByIdAndMemberId(orderItemId, memberId);
    //     return orderItem.isPresent(); // 존재하면 유효
    // }

    // 예시로, username을 통해 memberId를 찾는 로직을 구현
    private Long getMemberIdFromUsername(String username) {
        // 실제로는 username을 통해 DB에서 memberId를 조회하는 로직이 필요
        // 예시로 임의의 값 반환
        return 1L; // 임의의 값
    }
}
