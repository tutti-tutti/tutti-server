package com.tutti.server.core.review.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ReviewCreateRequest(

    @Schema(description = "상품 평점 (1~5)", example = "5")
    @NotNull(message = "상품 평점은 필수입니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")  // 평점 최소 1점
    @Max(value = 5, message = "평점은 5 이하이어야 합니다.")  // 평점 최대 5점
    Integer rating,  // 평점 (1~5)

    @Schema(description = "리뷰 내용", example = "좋아요!")
    @NotNull(message = "리뷰 내용은 필수입니다.")
    @Size(max = 500, message = "리뷰 내용은 500자 이내여야 합니다.")  // 길이 제한 500자
    String content,  // 리뷰 내용

    @Schema(description = "주문 아이템 ID", example = "1")
    @NotNull(message = "주문 아이템 ID는 필수입니다.")
    Long orderItemId,  // 주문 아이템 ID

    @Schema(description = "리뷰 작성자 닉네임", example = "tutti")
    @NotNull(message = "리뷰 작성자 닉네임은 필수입니다.")
    @Size(max = 20, message = "리뷰 작성자 닉네임은 20자 이내여야 합니다.")  // 길이 제한 20자
    String nickname,  // 리뷰 작성자 닉네임

    @Schema(description = "리뷰 이미지 URL 목록 (최대 4개)", example = "[\"https://tutti.com/image1.jpg\", \"https://tutti.com/image2.jpg\"]")
    List<String> reviewImages  // 이미지 URL 목록 (최대 4개)
) {

}
