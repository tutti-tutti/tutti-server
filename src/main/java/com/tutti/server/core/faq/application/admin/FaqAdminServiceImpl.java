package com.tutti.server.core.faq.application.admin;


import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminServiceImpl implements FaqAdminService {

    private final FaqAdminCreateServiceImpl faqAdminCreateServiceImpl;

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest) {
        return faqAdminCreateServiceImpl.createFaq(faqCreateRequest);
    }


}
