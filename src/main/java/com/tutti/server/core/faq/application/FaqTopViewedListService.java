package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqTopViewedListService {

    private final FaqRepository faqRepository;

    public FaqTopViewedListService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Transactional(readOnly = true)
    public List<FaqResponse> getTopFaqs(int limit) {
        PageRequest pageable = PageRequest.of(0, limit);
        return faqRepository.findTopFaqs(pageable)
            .stream()
            .map(FaqResponse::fromEntity)
            .toList();
    }
}
