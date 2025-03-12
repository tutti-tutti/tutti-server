package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.FaqService;
import com.tutti.server.core.faq.payload.request.FaqDetailRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FAQ 관련 API 요청을 처리하는 컨트롤러입니다. FAQ 조회, 검색, 카테고리 조회 등의 기능을 제공합니다.
 */
@Tag(name = "FAQ", description = "FAQ 관련 API")
@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    /**
     * FAQ 카테고리 목록을 조회합니다.
     *
     * @return 중복이 제거된 FAQ 카테고리 목록
     */
    @Operation(summary = "FAQ 카테고리 목록 조회", description = "FAQ에서 사용되는 카테고리 목록을 반환합니다.")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(faqService.getCategories());
    }

    /**
     * 조회수가 높은 상위 10개의 FAQ를 조회합니다.
     *
     * @return 조회수가 높은 상위 10개의 FAQ 목록
     */
    @Operation(summary = "FAQ 인기 질문 목록 조회", description = "조회수가 가장 높은 상위 10개의 FAQ를 반환합니다.")
    @GetMapping("/top")
    public ResponseEntity<List<FaqResponse>> getTopFaqs() {
        return ResponseEntity.ok(faqService.getTopFaqs(10));
    }

    /**
     * 검색어 및 카테고리 필터를 기준으로 FAQ 목록을 조회합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색 조건에 맞는 FAQ 목록
     */
    @Operation(summary = "FAQ 목록 조회", description = "카테고리 및 검색어를 기반으로 FAQ 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request) {
        return ResponseEntity.ok(faqService.getFaqs(request));
    }

    /**
     * 특정 FAQ를 조회합니다.
     *
     * @param request 조회할 FAQ ID를 포함한 요청 객체
     * @return 조회된 FAQ 정보
     */
    @Operation(summary = "FAQ 단건 조회", description = "FAQ ID를 기반으로 특정 FAQ 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "FAQ 조회 성공", content = @Content(schema = @Schema(implementation = FaqResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (FAQ ID 없음)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/detail")
    public ResponseEntity<FaqResponse> getFaqById(@RequestBody FaqDetailRequest request) {
        return ResponseEntity.ok(faqService.getFaqById(request.faqId()));
    }

    /**
     * 특정 키워드를 포함하는 FAQ를 검색합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색된 FAQ 목록
     */
    @Operation(summary = "FAQ 검색", description = "특정 키워드를 포함하는 FAQ를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request) {
        return ResponseEntity.ok(
            faqService.searchFaqs(request.query(), request.page(), request.size()));
    }

}
