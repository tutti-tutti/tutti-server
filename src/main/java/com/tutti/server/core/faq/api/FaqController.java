package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.FaqService;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqRequest;
import com.tutti.server.core.faq.payload.response.FaqFeedbackResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Object> getCategories() {
        return ResponseEntity.ok(faqService.getCategories());
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

    @Operation(summary = "FAQ 단건 조회", description = "FAQ ID를 기반으로 특정 FAQ 정보를 조회합니다.")
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponse> getFaqById(
        @Parameter(description = "조회할 FAQ ID", example = "1") @PathVariable Long faqId) {

        return ResponseEntity.ok(faqService.getFaqById(faqId));
    }

    @Operation(summary = "FAQ 등록", description = "새로운 FAQ를 등록합니다.")
    @PostMapping
    public ResponseEntity<FaqResponse> createFaq(@Valid @RequestBody FaqRequest request) {
        return ResponseEntity.ok(faqService.createFaq(request));
    }

    @Operation(summary = "FAQ 수정", description = "기존 FAQ 정보를 수정합니다.")
    @PutMapping("/{faqId}")
    public ResponseEntity<FaqResponse> updateFaq(
        @Parameter(description = "수정할 FAQ ID", example = "1") @PathVariable Long faqId,
        @Valid @RequestBody FaqRequest request) {

        return ResponseEntity.ok(faqService.updateFaq(faqId, request));
    }

    @Operation(summary = "FAQ 삭제", description = "FAQ ID를 기반으로 FAQ를 삭제 처리합니다.")
    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> deleteFaq(
        @Parameter(description = "삭제할 FAQ ID", example = "1") @PathVariable Long faqId) {

        faqService.deleteFaq(faqId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "FAQ 조회수 증가", description = "FAQ를 조회하면 자동으로 조회수를 증가시킵니다.")
    @PatchMapping("/{faqId}/view")
    public ResponseEntity<Map<String, Object>> incrementViewCount(
        @Parameter(description = "조회수를 증가시킬 FAQ ID", example = "1") @PathVariable Long faqId) {

        long updatedViewCount = faqService.incrementViewCount(faqId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "FAQ 조회수가 증가하였습니다.");
        response.put("viewCnt", updatedViewCount);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "FAQ 검색", description = "특정 키워드를 포함하는 FAQ를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<FaqListResponse> searchFaqs(
        @Parameter(description = "검색 키워드", example = "쿠폰") @RequestParam String query,
        @Parameter(description = "페이지 번호 (기본값: 1)", example = "1") @RequestParam(defaultValue = "1") int page,
        @Parameter(description = "페이지당 데이터 개수 (기본값: 20)", example = "20") @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(faqService.searchFaqs(query, page, size));
    }

    @Operation(summary = "FAQ 피드백 반영", description = "FAQ에 대해 긍정(👍) 또는 부정(👎) 피드백을 남깁니다.")
    @PatchMapping("/{faqId}/feedback")
    public ResponseEntity<FaqFeedbackResponse> updateFeedback(
        @Parameter(description = "피드백을 반영할 FAQ ID", example = "1") @PathVariable Long faqId,
        @Valid @RequestBody FaqFeedbackRequest request) {

        return ResponseEntity.ok(faqService.updateFeedback(faqId, request));
    }
}
