package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "FAQ", description = "FAQ 관련 API")
@RequestMapping("/faqs")
public interface FaqApiSpec {

    @Operation(summary = "FAQ 카테고리 목록 조회", description = "FAQ에서 사용되는 카테고리 목록을 반환합니다.")
    @GetMapping("/categories")
    ResponseEntity<List<String>> getCategories();

    @Operation(summary = "FAQ 인기 질문 목록 조회", description = "조회수가 가장 높은 상위 10개의 FAQ를 반환합니다.")
    @GetMapping("/top")
    ResponseEntity<List<FaqResponse>> getTopFaqs();

    @Operation(summary = "FAQ 목록 조회", description = "카테고리 및 검색어를 기반으로 FAQ 목록을 조회합니다.")
    @GetMapping
    ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request);

    @Operation(summary = "FAQ 단건 조회", description = "FAQ ID를 기반으로 특정 FAQ 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "FAQ 조회 성공", content = @Content(schema = @Schema(implementation = FaqResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (FAQ ID 없음)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{faqId}")
    ResponseEntity<FaqResponse> getFaqById(@PathVariable Long faqId);

    @Operation(summary = "FAQ 검색", description = "특정 키워드를 포함하는 FAQ를 검색합니다.")
    @GetMapping("/search")
    ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request);

    @Operation(summary = "FAQ 피드백(긍정, 부정)", description = "FAQ에 대한 피드백을 등록합니다.")
    @PatchMapping("/{faqId}/feedback")
    void faqFeedback(@PathVariable Long faqId, @RequestBody FaqFeedbackRequest request);
}
