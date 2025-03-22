package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.domain.FaqMainCategory;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Tag(name = "FAQ", description = "FAQ 관련 API")
public interface FaqApiSpec {

    @Operation(summary = "FAQ 카테고리 전체 목록 조회", description = "FAQ 전체 카테고리 목록을 반환합니다.")
    ResponseEntity<List<FaqCategoryResponse>> getCategories();

    @Operation(summary = "FAQ 메인 카테고리 목록 조회", description = "FAQ에서 사용되는 메인 카테고리 목록을 반환합니다.")
    ResponseEntity<List<String>> getMainCategories();

    @Operation(summary = "FAQ 서브 카테고리 목록 조회", description = "FAQ에서 사용되는 서브 카테고리 목록을 반환합니다.")
    @Parameter(name = "category", description = "메인 카테고리명", required = true)
    ResponseEntity<List<String>> getSubcategories(FaqMainCategory category);

    @Operation(summary = "FAQ 인기 질문 목록 조회", description = "조회수가 가장 높은 상위 10개의 FAQ를 반환합니다.")
    ResponseEntity<List<FaqResponse>> getTopFaqs();

    @Operation(summary = "FAQ 목록 조회", description = "카테고리 및 검색어를 기반으로 FAQ 목록을 조회합니다.")
    @Parameters({
        @Parameter(name = "mainCategory", description = "메인 카테고리"),
        @Parameter(name = "subCategory", description = "서브 카테고리"),
        @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
        @Parameter(name = "size", description = "페이지 크기", example = "10")
    })
    ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request);

    @Operation(summary = "FAQ 단건 조회", description = "FAQ ID를 기반으로 특정 FAQ 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "FAQ 조회 성공", content = @Content(schema = @Schema(implementation = FaqResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameter(name = "faqId", description = "FAQ ID", required = true)
    ResponseEntity<FaqResponse> getFaqById(Long faqId);

    @Operation(summary = "FAQ 검색", description = "특정 키워드를 포함하는 FAQ를 검색합니다.")
    @Parameters({
        @Parameter(name = "keyword", description = "검색 키워드", example = "배송"),
        @Parameter(name = "page", description = "페이지 번호", example = "1"),
        @Parameter(name = "size", description = "페이지 크기", example = "10")
    })
    ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request);

    @Operation(summary = "FAQ 피드백 등록", description = "FAQ에 대한 긍정 또는 부정 피드백을 등록합니다.")
    @Parameters({
        @Parameter(name = "faqId", description = "FAQ ID", required = true)
    })
    @RequestBody(
        description = "FAQ 피드백 요청 객체",
        required = true,
        content = @Content(schema = @Schema(implementation = FaqFeedbackRequest.class))
    )
    void faqFeedback(Long faqId, FaqFeedbackRequest request);

    @Operation(summary = "FAQ 등록", description = "관리자가 FAQ를 등록합니다.")
    @RequestBody(
        description = "FAQ 생성 요청 객체",
        required = true,
        content = @Content(schema = @Schema(implementation = FaqCreateRequest.class))
    )
    ResponseEntity<FaqCreateResponse> createFaq(FaqCreateRequest faqCreateRequest);

    @Operation(summary = "FAQ 수정", description = "관리자가 FAQ를 수정합니다.")
    @Parameters({
        @Parameter(name = "faqId", description = "수정할 FAQ ID", required = true)
    })
    @RequestBody(
        description = "FAQ 수정 요청 객체",
        required = true,
        content = @Content(schema = @Schema(implementation = FaqUpdateRequest.class))
    )
    ResponseEntity<FaqUpdateResponse> updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest);

    @Operation(summary = "FAQ 삭제", description = "관리자가 FAQ를 삭제합니다.")
    @Parameter(name = "faqId", description = "삭제할 FAQ ID", required = true)
    @ApiResponse(responseCode = "204", description = "FAQ 삭제 성공")
    ResponseEntity<Void> deleteFaq(Long faqId);
}
