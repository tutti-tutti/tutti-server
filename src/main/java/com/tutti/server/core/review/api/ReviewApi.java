package com.tutti.server.core.review.api;

import com.tutti.server.core.review.application.ReviewCreateServiceImpl;
import com.tutti.server.core.review.application.ReviewDeleteService;
import com.tutti.server.core.review.application.ReviewService;
import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewCreateResponse;
import com.tutti.server.core.review.payload.response.ReviewDeleteResponse;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewApi implements ReviewApiSpec {

    private final ReviewCreateServiceImpl reviewCreateServiceImpl;
    private final ReviewService reviewService;
    private final ReviewDeleteService reviewDeleteService;

    @Override
    public ResponseEntity<String> createReview(ReviewCreateRequest reviewCreateRequest,
        Authentication authentication) {
        if (reviewCreateRequest == null) {
            throw new DomainException(ExceptionType.INVALID_INPUT);
        }

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        String userEmail = extractUserEmail(authentication);

        ReviewCreateResponse review = reviewCreateServiceImpl.createReview(
            reviewCreateRequest.withEmail(userEmail));

        return ResponseEntity.ok("리뷰 작성이 완료되었습니다.");
    }

    @Override
    public ResponseEntity<ReviewListResponse> getReviewList(long productId,
        ReviewListRequest reviewListRequest) {
        if (productId <= 0) {
            throw new DomainException(ExceptionType.PRODUCT_NOT_FOUND);
        }
        ReviewListResponse response = reviewService.getReviews(reviewListRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ReviewDeleteResponse> deleteReview(long reviewId,
        Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }
        if (reviewId <= 0) {
            throw new DomainException(ExceptionType.PRODUCT_REVIEW_NOT_FOUND);
        }

        String userEmail = extractUserEmail(authentication);

        boolean isDeleted = reviewDeleteService.deleteReview(reviewId, userEmail);
        if (!isDeleted) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        return ResponseEntity.ok(new ReviewDeleteResponse(true, "리뷰가 성공적으로 삭제되었습니다."));
    }

    private String extractUserEmail(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
    }
}
