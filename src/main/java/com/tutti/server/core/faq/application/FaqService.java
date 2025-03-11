package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
     * FAQ 조회수 상위 N개 (삭제되지 않고 isView가 true인 데이터만)
     */
    @Transactional(readOnly = true)
    public List<FaqResponse> getTopFaqs(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return faqRepository.findTopFaqs(pageable)
            .stream()
            .map(FaqResponse::fromEntity)
            .toList(); // Java 16부터 사용 가능
    }
}
