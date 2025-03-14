package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "FAQ", description = "FAQ 관련 API")
public interface FaqApiSpec {

    ResponseEntity<List<String>> getCategories();

    ResponseEntity<List<FaqResponse>> getTopFaqs();

    ResponseEntity<FaqListResponse> getFaqs(FaqListRequest request);

    ResponseEntity<FaqResponse> getFaqById(Long faqId);

    ResponseEntity<FaqListResponse> searchFaqs(FaqSearchRequest request);
}
