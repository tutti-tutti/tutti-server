package com.tutti.server.core.review.api;

import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;

    // 리뷰 작성 API
    @PostMapping
    public ResponseEntity<String> createReview(
//        @AuthenticationPrincipal UserDetails userDetails, // 인증된 사용자 정보
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) { // ReviewRequestDto로 전달받은 데이터

        // 사용자가 인증된 상태인지 확인
//        if (userDetails == null) {
//            return ResponseEntity.status(401).body("로그인 상태여야 리뷰를 작성할 수 있습니다.");
//        }

        try {
            // 리뷰 작성
            ReviewCreateResponse review = reviewCreateServiceImpl.createReview(reviewCreateRequest);
            return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}
