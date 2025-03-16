package com.tutti.server.core.review.api;

import com.tutti.server.core.member.application.AuthService;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewDeleteServiceImpl;
import com.tutti.server.core.review.application.ReviewLikeServiceImpl;
import com.tutti.server.core.review.application.ReviewListServiceImpl;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewDeleteRequest;
import com.tutti.server.core.review.payload.request.ReviewLikeRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;
    private final ReviewDeleteServiceImpl reviewDeleteServiceImpl;
    private final ReviewListServiceImpl reviewListServiceImpl;
    private final ReviewLikeServiceImpl reviewLikeServiceImpl;
    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final MemberRepository memberRepository;

    /**
     * 리뷰 작성 API 사용자가 상품에 대한 리뷰를 작성합니다. 리뷰 작성 시 평점과 리뷰 내용을 포함해야 하며, 유효한 주문 상품 ID가 필요합니다.
     *
     * @param reviewCreateRequest 리뷰 작성 요청 정보
     * @return ResponseEntity 성공적으로 작성된 리뷰의 ID 또는 에러 메시지
     */
    @Override
    @PostMapping
    public ResponseEntity<String> createReview(
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {

        try {
            ReviewCreateResponse review = reviewCreateServiceImpl.createReview(reviewCreateRequest);
            return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    /**
     * 리뷰 목록 조회 API 특정 상품에 대한 리뷰 목록을 조회합니다. 로그인하지 않은 사용자도 조회할 수 있으며, 요청 시 정렬 기준과 요청할 리뷰 개수를 선택할 수
     * 있습니다.
     *
     * @param reviewListRequest 리뷰 목록 조회 요청 정보
     * @return ResponseEntity 상품에 대한 리뷰 목록
     */
    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ReviewListResponse> getReviewList(
        @Valid ReviewListRequest reviewListRequest) {

        ReviewListResponse response = reviewListServiceImpl.getReviews(reviewListRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 삭제 API 사용자가 자신이 작성한 리뷰를 삭제합니다. 인증된 사용자만 해당 작업을 수행할 수 있습니다. JWT 토큰을 통해 사용자를 인증합니다.
     *
     * @param reviewId            삭제할 리뷰의 ID
     * @param authorizationHeader 요청 헤더에 포함된 JWT 토큰
     * @return ResponseEntity 삭제 성공 여부 및 에러 메시지
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtUtil.getEmail(token);
        ReviewDeleteRequest request = new ReviewDeleteRequest(reviewId, userEmail);

        try {
            ReviewDeleteResponse response = reviewDeleteServiceImpl.deleteReview(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId,
                "잘못된 요청입니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId, "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상품의 초기 10개 리뷰 조회 API 상품 상세 페이지에서 초기 10개의 리뷰를 최신순으로 조회합니다.
     *
     * @param productId 상품의 ID
     * @param size      조회할 리뷰 개수
     * @param sort      정렬 기준 (기본값: 최신순)
     * @return ResponseEntity 상품의 초기 10개 리뷰 목록
     */
    @Override
    @GetMapping("/{productId}/initialReviews")
    public ResponseEntity<ReviewListResponse> getInitialReviewList(
        @PathVariable("productId") Long productId,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "sort", defaultValue = "created_at_desc") String sort) {

        ReviewListResponse response = reviewListServiceImpl.getInitialReviews(productId, size,
            sort);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 리뷰 무한 스크롤 조회 API 특정 상품에 대한 리뷰를 무한 스크롤 방식으로 조회합니다. 페이지네이션을 처리할 수 있습니다.
     *
     * @param productId  상품의 ID
     * @param size       조회할 리뷰 개수
     * @param sort       정렬 기준 (기본값: 최신순)
     * @param nextCursor 다음 페이지 조회를 위한 커서 값
     * @return ResponseEntity 상품에 대한 리뷰 목록과 페이지네이션 정보
     */
    @Override
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<ReviewListResponse> getReviewsWithPagination(
        @PathVariable("productId") Long productId,
        @RequestParam(value = "size", defaultValue = "20") Integer size,
        @RequestParam(value = "sort", defaultValue = "created_at_desc") String sort,
        @RequestParam(value = "next_cursor", required = false) String nextCursor) {

        ReviewListResponse response = reviewListServiceImpl.getReviewsWithPagination(productId,
            size, sort,
            nextCursor);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 좋아요 추가 API 사용자가 특정 리뷰에 좋아요를 추가할 수 있습니다. 인증된 사용자만 사용 가능합니다.
     *
     * @param reviewId            좋아요를 추가할 리뷰의 ID
     * @param authorizationHeader 요청 헤더에 포함된 JWT 토큰
     * @return ResponseEntity 좋아요 추가 결과
     */
    @PostMapping("/{reviewId}/reviewLike")
    public ResponseEntity<String> likeReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtUtil.getEmail(token);

        Long memberId = convertEmailToMemberId(userEmail);

        ReviewLikeRequest request = new ReviewLikeRequest(reviewId, memberId);

        try {
            reviewLikeServiceImpl.likeReview(request);
            return ResponseEntity.ok("좋아요가 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    private Long convertEmailToMemberId(String userEmail) {

        Member member = memberRepository.findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        return member.getId();
    }

    /**
     * 리뷰 좋아요 취소 API 사용자가 자신이 좋아요를 누른 리뷰의 좋아요를 취소할 수 있습니다. 인증된 사용자만 사용 가능합니다.
     *
     * @param reviewId            좋아요를 취소할 리뷰의 ID
     * @param authorizationHeader 요청 헤더에 포함된 JWT 토큰
     * @return ResponseEntity 좋아요 취소 결과
     */
    @Override
    @DeleteMapping("/{reviewId}/reviewLike")
    public ResponseEntity<String> cancelLikeReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtUtil.getEmail(token);
        Long memberId = convertEmailToMemberId(userEmail);

        ReviewLikeRequest request = new ReviewLikeRequest(reviewId, memberId);

        try {
            reviewLikeServiceImpl.cancelLikeReview(request);
            return ResponseEntity.ok("좋아요가 취소되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}
