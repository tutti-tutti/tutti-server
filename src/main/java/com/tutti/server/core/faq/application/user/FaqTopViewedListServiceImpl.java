package com.tutti.server.core.faq.application.user;

import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqTopViewedListServiceImpl implements FaqTopViewedListService {

    private final FaqRepository faqRepository;

    @Transactional(readOnly = true)
    public List<FaqResponse> getTopFaqs(int limit) {
        try {
            PageRequest pageable = PageRequest.of(0, limit);
            List<FaqResponse> topFaqs = faqRepository.findTopFaqs(pageable)
                .stream()
                .map(FaqResponse::fromEntity)
                .toList();

            if (topFaqs.isEmpty()) {
                throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
            }
            return topFaqs;
        } catch (Exception e) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
    }
}
