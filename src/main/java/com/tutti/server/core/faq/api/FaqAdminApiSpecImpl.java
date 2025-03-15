package com.tutti.server.core.faq.api;

import com.tutti.server.core.faq.application.admin.FaqAdminServiceImpl;
import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("faqs")
@RequiredArgsConstructor
public class FaqAdminApiSpecImpl implements FaqAdminApiSpec {

    public final FaqAdminServiceImpl faqAdminServiceImpl;

    @Override
    public void createFaq(FaqCreateRequest faqCreateRequest) {

    }

//    @Override
//    public void updateFaq(Long faqId, FaqUpdateRequest faqUpdateRequest) {
//
//    }
//
//    @Override
//    public void deleteFaq(Long faqId) {
//
//    }
}
