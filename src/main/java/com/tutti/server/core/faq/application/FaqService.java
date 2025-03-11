package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
     * FAQ 조회 (삭제되지 않고 isView가 true인 데이터만)
     */
    @Transactional(readOnly = true)
    public FaqListResponse getFaqs(String query, String category, String subcategory, int page,
        int size) {
        Page<Faq> faqs;

        if (query != null && !query.isEmpty()) {
            // 검색어 기반 조회
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                query, PageRequest.of(page - 1, size));
        } else if (category != null && subcategory != null) {
            // 카테고리 기반 조회
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                category, subcategory, PageRequest.of(page - 1, size));
        } else {
            // 전체 조회
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(
                PageRequest.of(page - 1, size));
        }

        List<FaqResponse> faqResponses = faqs.getContent().stream()
            .map(faq -> new FaqResponse(
                faq.getId(),
                faq.getCategoryName(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getViewCnt()
            ))
            .collect(Collectors.toList());

        return FaqListResponse.from(
            (int) faqs.getTotalElements(),
            page,
            size,
            faqResponses
        );
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
