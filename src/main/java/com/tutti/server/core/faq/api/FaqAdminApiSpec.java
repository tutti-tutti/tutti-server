package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "FAQ Admin API", description = "관리자를 위한 FAQ 관리 API")
public interface FaqAdminApiSpec {

    @Operation(summary = "FAQ 등록", description = "관리자가 FAQ를 등록한다.")
    @PostMapping("/api/faqs")
    void createFaq(
        @RequestBody FaqCreateRequest faqCreateRequest
    );

//    @Operation(summary = "FAQ 수정", description = "관리자가 기존 FAQ를 수정한다.")
//    @PutMapping("/api/faqs/{faqId}")
//    void updateFaq(
//        @PathVariable Long faqId,
//        @RequestBody FaqUpdateRequest faqUpdateRequest
//    );
//
//    @Operation(summary = "FAQ 삭제", description = "관리자가 FAQ를 삭제한다. 삭제된 FAQ는 소프트 삭제로 처리된다.")
//    @DeleteMapping("/api/faqs/{faqId}")
//    void deleteFaq(
//        @PathVariable Long faqId
//    );
}
