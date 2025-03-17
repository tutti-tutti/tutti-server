package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.admin.FaqAdminServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        FaqCreateResponse response = faqAdminServiceImpl.createFaq(faqCreateRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{faqId}")
    public ResponseEntity<FaqUpdateResponse> updateFaq(
        @PathVariable Long faqId,
        @RequestBody FaqUpdateRequest faqUpdateRequest) {
        FaqUpdateResponse response = faqAdminServiceImpl.updateFaq(faqId, faqUpdateRequest);
        return ResponseEntity.ok(response);
    }
}
