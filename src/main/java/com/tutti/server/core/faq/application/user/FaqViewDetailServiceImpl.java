package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqViewDetailServiceImpl implements FaqViewDetailService {

    private final FaqRepository faqRepository;
    private final FaqIncrementViewCountServiceImpl faqIncrementViewCountServiceImpl;

    @Transactional
    public FaqResponse findFaqById(Long faqId) {
        try {
            Faq faq = faqRepository.findOne(faqId);
            if (faq == null) {
                throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
            }

            faqIncrementViewCountServiceImpl.incrementViewCount(faqId);
            return FaqResponse.fromEntity(faq);
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }
}
