package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.FaqService;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "FAQ", description = "FAQ 관련 API")
@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @Operation(summary = "FAQ 카테고리 목록 조회", description = "FAQ에서 사용되는 카테고리 목록을 반환합니다.")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(
            faqService.getCategories());// faqService.getCategories() -> faqCategoryRepository.findDistinctMainCategories()
    }

    @Operation(summary = "FAQ 인기 질문 목록 조회", description = "조회수가 가장 높은 상위 10개의 FAQ를 반환합니다.")
    @GetMapping("/top")
    public ResponseEntity<List<FaqResponse>> getTopFaqs() {
        return ResponseEntity.ok(faqService.getTopFaqs(10)); // 상위 10개 FAQ 반환
    }

    @Operation(summary = "FAQ 목록 조회", description = "카테고리 및 검색어를 기반으로 FAQ 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<FaqListResponse> getFaqs(
        @Parameter(description = "검색어 (질문 키워드)", example = "환불") @RequestParam(required = false) String query,
        @Parameter(description = "카테고리명", example = "배송") @RequestParam(required = false) String category,
        @Parameter(description = "서브카테고리명", example = "국제 배송") @RequestParam(required = false) String subcategory,
        @Parameter(description = "페이지 번호 (기본값: 1)", example = "1") @RequestParam(defaultValue = "1") int page,
        @Parameter(description = "페이지당 데이터 개수 (기본값: 10)", example = "10") @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(faqService.getFaqs(query, category, subcategory, page, size));
    }
}
