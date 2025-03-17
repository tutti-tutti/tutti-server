package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.request.FaqUpdateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.faq.payload.response.FaqUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminServiceImpl implements FaqAdminService {

    private final FaqAdminCreateService faqAdminCreateService;
    private final FaqAdminUpdateService faqAdminUpdateService;

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest) {
        return faqAdminCreateService.createFaq(faqCreateRequest);
    }

    @Override
    public FaqUpdateResponse updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest) {
        return faqAdminUpdateService.updateFaq(faqId, faqUpdateRequest);
    }
}
