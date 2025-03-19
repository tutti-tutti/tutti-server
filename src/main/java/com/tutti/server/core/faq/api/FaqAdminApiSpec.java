package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "FAQ 관리자 API")
public interface FaqAdminApiSpec {

    @Operation(summary = "FAQ 등록", description = "관리자가 FAQ를 등록한다.")
    ResponseEntity<FaqCreateResponse> createFaq(@RequestBody FaqCreateRequest faqCreateRequest);

    @Operation(summary = "FAQ 수정", description = "관리자가 FAQ를 수정한다.")
    ResponseEntity<FaqUpdateResponse> updateFaq(
        @PathVariable Long faqId,
        @RequestBody FaqUpdateRequest faqUpdateRequest);

    @Operation(summary = "FAQ 삭제", description = "관리자가 FAQ를 삭제한다.")
    ResponseEntity<Void> deleteFaq(@PathVariable Long faqId);
}
