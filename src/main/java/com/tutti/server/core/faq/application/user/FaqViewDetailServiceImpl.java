package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
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

        Faq faq = faqRepository.findOne(faqId);
        faqIncrementViewCountServiceImpl.incrementViewCount(faqId);
        return FaqResponse.fromEntity(faq);
    }

}
