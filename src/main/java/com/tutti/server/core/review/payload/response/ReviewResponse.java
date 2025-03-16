package com.tutti.server.core.review.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "리뷰 응답")
public record ReviewResponse(
    @Schema(description = "리뷰 ID", example = "1") Long id,
    @Schema(description = "상품 ID", example = "1") Long productId,
    @Schema(description = "리뷰 작성자 닉네임", example = "tutti") String nickname,
    @Schema(description = "리뷰 내용", example = "상품이 좋아요!") String content,
    @Schema(description = "평점", example = "5") Integer rating,
    @Schema(description = "리뷰 작성일", example = "2022-01-01 T10:00")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime createdAt

) {

}
