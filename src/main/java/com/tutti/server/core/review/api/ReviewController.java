package com.tutti.server.core.review.api;

import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.domain.Review;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성 API
    @PostMapping
    public ResponseEntity<String> createReview(
//        @AuthenticationPrincipal UserDetails userDetails, // 인증된 사용자 정보
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) { // ReviewRequestDto로 전달받은 데이터

        // 사용자가 인증된 상태인지 확인
//        if (userDetails == null) {
//            return ResponseEntity.status(401).body("로그인 상태여야 리뷰를 작성할 수 있습니다.");
//        }

        // 리뷰 작성
        Review review = reviewService.createReview(//userDetails.getUsername()
            "testUser", reviewCreateRequest);

        return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
    }
}
