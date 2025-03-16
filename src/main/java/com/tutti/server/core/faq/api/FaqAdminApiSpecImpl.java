package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.admin.FaqAdminServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
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

    public final FaqAdminServiceImpl faqAdminServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<FaqCreateResponse> createFaq(
        @RequestBody FaqCreateRequest faqCreateRequest) {
        return ResponseEntity.ok(faqAdminServiceImpl.createFaq(faqCreateRequest));
    }

}
