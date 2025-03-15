package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "FAQ 관리자 API")
public interface FaqAdminApiSpec {

    @Operation(summary = "FAQ 등록", description = "관리자가 FAQ를 등록한다.")
    ResponseEntity<FaqCreateResponse> createFaq(@RequestBody FaqCreateRequest faqCreateRequest);
}
