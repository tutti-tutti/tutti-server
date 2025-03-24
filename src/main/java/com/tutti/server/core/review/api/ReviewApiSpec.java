package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "리뷰 API")
public interface ReviewApiSpec {

    @Operation(
            summary = "감성 분석 요청 API",
            description = "리뷰 텍스트를 FastAPI 감성 분석 모델에 전달하여, "
                    + "긍정 또는 부정으로 분류하고 신뢰도를 반환합니다."
    )
    SentimentResponse analyzeSentiment(
            @RequestBody(description = "감성 분석 요청 본문") SentimentRequest request
    );

    @Operation(
            summary = "감성 분석 피드백 요청 API",
            description = "사용자의 피드백을 기반으로 감성 분석 모델을 재학습합니다."
                    + "예측이 올바르다면 'Correct', 잘못되었다면 'Incorrect' 값을 전달해 주세요."
    )
    SentimentFeedbackResponse sendFeedback(
            @RequestBody(description = "피드백 요청 본문") SentimentFeedbackRequest request
    );

    @Operation(summary = "리뷰 작성 API")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<String> createReview(
            ReviewCreateRequest reviewCreateRequest, CustomUserDetails user);

    @Operation(summary = "리뷰 목록 조회 API")
    ResponseEntity<ReviewListResponse> getReviewList(Long productId, Long cursor, int size,
            String sort);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "리뷰 상세 조회 API")
    ResponseEntity<ReviewDetailResponse> getReviewDetail(long reviewId, CustomUserDetails user);

    @Operation(summary = "개인 리뷰 목록 조회 API")
    ResponseEntity<ReviewMyListResponse> getMyReviewList(String nickname, Long cursor, int size);

    @Operation(summary = "리뷰 삭제 API")
    ResponseEntity<ReviewDeleteResponse> deleteMyReview(Long reviewId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "리뷰 도움이 되었어요 API")
    ResponseEntity<ReviewLikeResponse> likeReview(Long id, CustomUserDetails user);

    @Operation(summary = "리뷰 별점 평균 API")
    ResponseEntity<ReviewRatingResponse> ratingAverage(Long productId);
}
