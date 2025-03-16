package com.tutti.server.core.faq.application.admin;

import com.tutti.server.core.faq.payload.request.FaqCreateRequest;
import com.tutti.server.core.faq.payload.response.FaqCreateResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAdminServiceImpl implements FaqAdminService {

    private final FaqAdminCreateServiceImpl faqAdminCreateServiceImpl;

    @Override
    public FaqCreateResponse createFaq(FaqCreateRequest faqCreateRequest) {
        try {
            return faqAdminCreateServiceImpl.createFaq(faqCreateRequest);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_FEEDBACK_FAILED,
                "FAQ 생성 중 알 수 없는 오류가 발생했습니다.");
        }
    }
}
