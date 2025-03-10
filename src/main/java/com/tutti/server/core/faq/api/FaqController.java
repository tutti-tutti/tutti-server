package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
