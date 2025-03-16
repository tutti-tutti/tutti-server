package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.admin.FaqAdminServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("faqs")
@RequiredArgsConstructor
public class FaqAdminApiSpecImpl implements FaqAdminApiSpec {

    private final FaqAdminServiceImpl faqAdminServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<FaqCreateResponse> createFaq(
        @RequestBody FaqCreateRequest faqCreateRequest) {
        try {
            FaqCreateResponse response = faqAdminServiceImpl.createFaq(faqCreateRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new DomainException(ExceptionType.MISSING_REQUIRED_FIELD);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED);
        }
    }
}
