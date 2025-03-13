package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqSearchListService {

    private final FaqRepository faqRepository;

    public FaqSearchListService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Transactional(readOnly = true)
    public FaqListResponse searchFaqs(FaqListRequest request) {
        // 페이지 요청 생성
        PageRequest pageRequest = PageRequest.of(request.page() - 1, request.size());

        // FAQ 검색
        Page<FaqResponse> faqResponses = findFaqs(request, pageRequest);

        // FAQ 목록 응답 생성
        return new FaqListResponse(
            (int) faqResponses.getTotalElements(), // 총 FAQ 수
            request.page(),                       // 현재 페이지
            request.size(),                       // 페이지 크기
            new FaqSearchResponse(faqResponses.getContent()) // 결과 콘텐츠
        );
    }

    private Page<FaqResponse> findFaqs(FaqListRequest request, PageRequest pageRequest) {
        // 기본적으로 빈 페이지 선언
        Page<Faq> faqs;

        // 쿼리 조건에 따른 처리
        if (request.query() != null && !request.query().isEmpty()) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);
        } else if (request.category() != null && request.subcategory() != null) {
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);
        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
        }

        // Faq 엔티티를 FaqResponse 레코드로 변환 후 반환
        return faqs.map(FaqResponse::fromEntity);
    }
}
