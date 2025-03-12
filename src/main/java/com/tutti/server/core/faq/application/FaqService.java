package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import java.util.List;
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
     *
     * @return 중복 제거된 FAQ 메인 카테고리 목록
     */
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }

    /**
     * FAQ 목록 조회 (삭제되지 않고 isView가 true인 데이터만)
     *
     * @param request FAQ 검색 조건을 담은 요청 객체
     * @return 검색 조건에 맞는 FAQ 목록 응답 객체
     */
    @Transactional(readOnly = true)
    public FaqListResponse getFaqs(FaqSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.page() - 1, request.size());
        Page<FaqResponse> faqResponses = findFaqs(request, pageRequest);

        return new FaqListResponse(
            (int) faqResponses.getTotalElements(),
            request.page(),
            request.size(),
            faqResponses.getContent()
        );
    }

    /**
     * FAQ 검색 조건을 기반으로 FAQ 데이터를 조회
     *
     * @param request     FAQ 검색 조건을 담은 요청 객체
     * @param pageRequest 페이지네이션 정보
     * @return FAQ 목록을 FaqResponse 형태로 변환한 Page 객체
     */
    private Page<FaqResponse> findFaqs(FaqSearchRequest request, PageRequest pageRequest) {
        Page<Faq> faqs;

        // 검색어(query)가 있는 경우 해당 키워드를 포함하는 FAQ 조회
        if (request.query() != null && !request.query().isEmpty()) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);

            // 특정 카테고리 및 서브카테고리 기반 조회
        } else if (request.category() != null && request.subcategory() != null) {
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);

            // 기본적으로 삭제되지 않고 isView가 true인 모든 FAQ 조회
        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
        }

        // Page<Faq> → Page<FaqResponse> 변환
        return faqs.map(faq -> new FaqResponse(
            faq.getId(),
            faq.getCategoryName(),
            faq.getQuestion(),
            faq.getAnswer(),
            faq.getViewCnt()
        ));
    }

    /**
     * 조회수가 높은 FAQ 상위 N개 조회 (삭제되지 않고 isView가 true인 데이터만)
     *
     * @param limit 조회할 FAQ 개수
     * @return 조회수가 높은 FAQ 목록을 FaqResponse 형태로 변환한 리스트
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
