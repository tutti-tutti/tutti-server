package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.review.payload.request.LatestReviewCursor;
import com.tutti.server.core.review.payload.request.LikeReviewCursor;
import com.tutti.server.core.review.payload.request.RatingReviewCursor;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.SentimentFeedbackRequest;
import com.tutti.server.core.review.payload.request.SentimentRequest;
import com.tutti.server.core.review.payload.response.LatestReviewListResponse;
import com.tutti.server.core.review.payload.response.LikeReviewListResponse;
import com.tutti.server.core.review.payload.response.RatingReviewListResponse;
import com.tutti.server.core.review.payload.response.ReviewCountPerStarResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewDetailResponse;
import com.tutti.server.core.review.payload.response.ReviewLikeResponse;
import com.tutti.server.core.review.payload.response.ReviewMyListResponse;
import com.tutti.server.core.review.payload.response.ReviewRatingResponse;
import com.tutti.server.core.review.payload.response.SentimentFeedbackResponse;
import com.tutti.server.core.review.payload.response.SentimentPositiveAvgResponse;
import com.tutti.server.core.review.payload.response.SentimentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "리뷰 최신순 목록 조회 API (createdAt DESC)")
    ResponseEntity<LatestReviewListResponse> getLatestReviewList(
            @Parameter(description = "상품 ID", example = "49")
            Long productId,

            @Parameter(
                    description = "커서 ID (이전 페이지의 마지막 리뷰 ID), 첫 요청 시 null",
                    required = false
            )
            LatestReviewCursor cursor,

            @Parameter(description = "가져올 리뷰 수 (기본값: 20)", example = "20")
            int size
    );

    @Operation(summary = "리뷰 평점순 목록 조회 API (rating DESC, id DESC)")
    ResponseEntity<RatingReviewListResponse> getRatingReviewList(
            @Parameter(description = "상품 ID", example = "101")
            Long productId,

            @Parameter(
                    description = "커서 정보 (마지막 리뷰의 rating과 ID), 첫 요청 시 null",
                    required = false
            )
            RatingReviewCursor ratingCursor,

            @Parameter(description = "가져올 리뷰 수 (기본값: 20)", example = "20")
            int size
    );

    @Operation(summary = "리뷰 '도움이 되었어요' 순 목록 조회 API (likeCount DESC, id DESC)")
    ResponseEntity<LikeReviewListResponse> getLikeReviewList(
            @Parameter(description = "상품 ID", example = "101")
            Long productId,

            @Parameter(
                    description = "커서 정보 (마지막 리뷰의 likeCount와 ID), 첫 요청 시 null",
                    required = false
            )
            LikeReviewCursor likeCursor,

            @Parameter(description = "가져올 리뷰 수 (기본값: 20)", example = "20")
            int size
    );

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "리뷰 상세 조회 API")
    ResponseEntity<ReviewDetailResponse> getReviewDetail(long reviewId, CustomUserDetails user);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "개인 작성 리뷰 목록 조회 API")
    ResponseEntity<ReviewMyListResponse> getMyReviewList(CustomUserDetails user, Long cursor,
            int size);

    @Operation(summary = "리뷰 삭제 API")
    ResponseEntity<ReviewDeleteResponse> deleteMyReview(Long reviewId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "리뷰 도움이 되었어요 API")
    ResponseEntity<ReviewLikeResponse> likeReview(Long id, CustomUserDetails user);

    @Operation(summary = "리뷰 별점 평균 API")
    ResponseEntity<ReviewRatingResponse> ratingAverage(Long productId);

    @Operation(summary = "리뷰 점수별 리뷰 갯수 API")
    ResponseEntity<ReviewCountPerStarResponse> countStar(Long productId);

    @Operation(summary = "리뷰 감성분석 긍정률 API")
    ResponseEntity<SentimentPositiveAvgResponse> senPositive(Long productId);
}
