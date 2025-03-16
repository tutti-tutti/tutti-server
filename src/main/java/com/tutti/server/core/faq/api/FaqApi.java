package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.user.FaqServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
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
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FaqApi implements FaqApiSpec {

    private final FaqServiceImpl faqServiceImpl;

    @Override
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        try {
            return ResponseEntity.ok(faqServiceImpl.getCategories());
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND);
        }
    }

    @Override
    @GetMapping("/top")
    public ResponseEntity<List<FaqResponse>> getTopFaqs() {
        try {
            return ResponseEntity.ok(faqServiceImpl.getTopFaqs(10));
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<FaqListResponse> getFaqs(@Valid FaqListRequest request) {
        try {
            return ResponseEntity.ok(faqServiceImpl.getFaqs(request));
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    @Override
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponse> getFaqById(@PathVariable Long faqId) {
        try {
            if (faqId == null || faqId <= 0) {
                throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
            }
            FaqResponse faqResponse = faqServiceImpl.getFaqById(faqId);
            return ResponseEntity.ok(faqResponse);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<FaqListResponse> searchFaqs(@Valid FaqSearchRequest request) {
        try {
            return ResponseEntity.ok(faqServiceImpl.searchFaqs(request));
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }

    @Override
    @PatchMapping("/{faqId}/feedback")
    public void faqFeedback(@PathVariable Long faqId, @Valid FaqFeedbackRequest request) {
        try {
            faqServiceImpl.faqFeedback(faqId, request);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED);  // 예외 처리
        }
    }

    @Override
    @PatchMapping("/{faqId}/feedback")
    public void faqFeedback(@PathVariable Long faqId, @Valid FaqFeedbackRequest request) {
        faqServiceImpl.faqFeedback(faqId, request);
    }
}
