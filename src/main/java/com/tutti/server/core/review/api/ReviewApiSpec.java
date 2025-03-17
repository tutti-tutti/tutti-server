package com.tutti.server.core.review.api;

import com.tutti.server.core.review.payload.request.ReviewCreateRequest;
import com.tutti.server.core.review.payload.request.ReviewListRequest;
import com.tutti.server.core.review.payload.response.ReviewListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "리뷰 문서")
@RequestMapping("/reviews")
public interface ReviewApiSpec {

    @Operation(summary = "리뷰 작성 API")
    @PostMapping
    ResponseEntity<String> createReview(
        @RequestBody ReviewCreateRequest reviewCreateRequest);

    @Operation(summary = "리뷰 목록 조회 API")
    @GetMapping("/{productId}")
    ResponseEntity<ReviewListResponse> getReviewList(ReviewListRequest reviewListRequest);
}
