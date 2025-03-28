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
    private final FaqAdminDeleteService faqAdminDeleteService;

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest, Long memberId) {
        return faqAdminCreateService.createFaq(faqCreateRequest, memberId);
    }

    @Override
    public FaqUpdateResponse updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest,
            Long memberId) {
        return faqAdminUpdateService.updateFaq(faqId, faqUpdateRequest, memberId);
    }

    @Override
    public void deleteFaq(Long faqId, Long memberId) {
        faqAdminDeleteService.deleteFaq(faqId, memberId);
    }
}
