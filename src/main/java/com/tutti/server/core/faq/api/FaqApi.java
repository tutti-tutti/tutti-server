package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.FaqServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqApi implements FaqApiSpec {

    private final FaqServiceImpl faqServiceImpl;

    @Override
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(faqServiceImpl.getCategories());
    }

    @Override
    @GetMapping("/top")
    public ResponseEntity<List<FaqResponse>> getTopFaqs() {
        return ResponseEntity.ok(faqServiceImpl.getTopFaqs(10));
    }

    @Override
    @GetMapping
    public ResponseEntity<FaqListResponse> getFaqs(@Valid FaqListRequest request) {
        return ResponseEntity.ok(faqServiceImpl.getFaqs(request));
    }

    @Override
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponse> getFaqById(Long faqId) {
        FaqResponse faqResponse = faqServiceImpl.getFaqById(faqId);
        return ResponseEntity.ok(faqResponse);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<FaqListResponse> searchFaqs(@Valid FaqSearchRequest request) {
        return ResponseEntity.ok(faqServiceImpl.searchFaqs(request));
    }

    @Override
    @PatchMapping("/{faqId}/feedback")
    public void faqFeedback(@PathVariable Long faqId, @Valid FaqFeedbackRequest request) {
        faqServiceImpl.faqFeedback(faqId, request);
    }
}
