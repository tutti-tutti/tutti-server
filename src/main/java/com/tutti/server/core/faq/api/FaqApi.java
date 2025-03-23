package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.admin.FaqAdminServiceImpl;
import com.tutti.server.core.faq.application.user.FaqServiceImpl;
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
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faqs")
public class FaqApi implements FaqApiSpec {

    private final FaqServiceImpl faqServiceImpl;
    private final FaqAdminServiceImpl faqAdminServiceImpl;

    // ======================== [ 사용자용 FAQ API ] ========================

    @Override
    @GetMapping("/categories/mainCategories")
    public ResponseEntity<List<String>> getMainCategories() {
        return ResponseEntity.ok(faqServiceImpl.getMainCategories());
    }

    @Override
    @GetMapping("/categories/{category}/subcategories")
    public ResponseEntity<List<String>> getSubcategories(@PathVariable FaqMainCategory category) {
        return ResponseEntity.ok(faqServiceImpl.getSubCategories(category));
    }

    @Override
    @GetMapping("/categories")
    public ResponseEntity<List<FaqCategoryResponse>> getCategories() {
        return ResponseEntity.ok(faqServiceImpl.getCategories());
    }

    @Override
    @GetMapping("/top")
    public ResponseEntity<List<FaqResponse>> getTopFaqs() {
        return ResponseEntity.ok(faqServiceImpl.getTopFaqs(10));
    }

    @Override
    @GetMapping
    public ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request) {
        return ResponseEntity.ok(faqServiceImpl.getFaqs(request));
    }

    @Override
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponse> getFaqById(@PathVariable Long faqId) {
        return ResponseEntity.ok(faqServiceImpl.getFaqById(faqId));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request) {
        return ResponseEntity.ok(faqServiceImpl.searchFaqs(request));
    }

    @Override
    @PatchMapping("/{faqId}/feedback")
    public void faqFeedback(@PathVariable Long faqId, @RequestBody FaqFeedbackRequest request) {
        faqServiceImpl.faqFeedback(faqId, request);
    }

    // ======================== [ 관리자용 FAQ API ] ========================

    @Override
    @PostMapping("/admin")
    public ResponseEntity<FaqCreateResponse> createFaq(
        @RequestBody FaqCreateRequest faqCreateRequest) {
        FaqCreateResponse response = faqAdminServiceImpl.createFaq(faqCreateRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/admin/{faqId}")
    public ResponseEntity<FaqUpdateResponse> updateFaq(@PathVariable Long faqId,
        @RequestBody FaqUpdateRequest faqUpdateRequest) {
        FaqUpdateResponse response = faqAdminServiceImpl.updateFaq(faqId, faqUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/{faqId}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long faqId) {
        faqAdminServiceImpl.deleteFaq(faqId);
        return ResponseEntity.noContent().build();
    }
}

