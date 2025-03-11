package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Builder
@RequiredArgsConstructor

public class FaqService {

    private final FaqCategoryRepository faqCategoryRepository;
    private final FaqRepository faqRepository;

    /**
     * FAQ 카테고리 목록 조회
     */
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }

    /**
     * FAQ 조회수 상위 10개 (삭제되지 않고 isView가 true인 데이터만)
     */
    public List<FaqResponse> getTopFaqs(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return faqRepository.findTopFaqs(pageable)
            .stream()
            .map(FaqResponse::fromEntity)
            .collect(Collectors.toList());
    }

}
