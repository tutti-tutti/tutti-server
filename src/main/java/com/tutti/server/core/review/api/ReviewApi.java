package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.AuthService;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewDeleteService;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewDeleteRequest;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;
    private final ReviewDeleteService reviewDeleteService;
    private final ReviewService reviewService;
    private final JWTUtil jwtUtil;
    private final AuthService authService;

    // 리뷰 작성 API
    @Override
    @PostMapping
    public ResponseEntity<String> createReview(
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {

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

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ReviewListResponse> getReviewList(
        @Valid ReviewListRequest reviewListRequest) {

        ReviewListResponse response = reviewService.getReviews(reviewListRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestHeader("Authorization") String authorizationHeader) {

        // JWT 토큰에서 사용자 정보 추출 (Authorization 헤더에서 토큰 가져오기)
        String token = authorizationHeader.replace("Bearer ", "");  // Bearer prefix 제거
        String userEmail = jwtUtil.getEmail(token);  // JWT 토큰에서 이메일 추출
        System.out.println("tokenboseokjjang: " + token);
        // 요청 객체 생성
        ReviewDeleteRequest request = new ReviewDeleteRequest(reviewId, userEmail);

        try {
            // 리뷰 삭제 서비스 호출
            ReviewDeleteResponse response = reviewDeleteService.deleteReview(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            // 잘못된 요청에 대해 400 반환
            ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId,
                "잘못된 요청입니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            // 서버 오류에 대해 500 반환
            ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId, "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
