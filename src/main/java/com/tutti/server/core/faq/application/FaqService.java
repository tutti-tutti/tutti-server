package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqSearchRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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
     * FAQ 목록 조회 (삭제되지 않고, isView가 true인 데이터만)
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색 조건에 맞는 FAQ 목록
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
     * 조회수가 높은 FAQ 상위 N개 조회
     *
     * @param limit 조회할 FAQ 개수
     * @return 조회수가 높은 FAQ 목록
     */
    @Transactional(readOnly = true)
    public List<FaqResponse> getTopFaqs(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return faqRepository.findTopFaqs(pageable)
            .stream()
            .map(FaqResponse::fromEntity)
            .toList();
    }

    /**
     * FAQ 단건 조회 (삭제되지 않고, isView가 true인 데이터만) 조회 시 조회수를 증가시킴 (비동기 처리)
     *
     * @param faqId 조회할 FAQ ID
     * @return 조회된 FAQ 정보
     */
    @Transactional
    public FaqResponse getFaqById(Long faqId) {
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new EntityNotFoundException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        incrementViewCount(faqId); // 조회수 증가 (비동기 실행)

        return FaqResponse.fromEntity(faq);
    }

    /**
     * FAQ 조회수 증가 (비동기 실행) 새로운 트랜잭션에서 실행되며, 기존 요청 응답과 독립적으로 실행됨
     *
     * @param faqId 조회수가 증가할 FAQ ID
     */
    @Async
    @Transactional
    public void incrementViewCount(Long faqId) {
        faqRepository.incrementViewCount(faqId);
    }

    /**
     * 검색 조건을 기반으로 FAQ 데이터를 조회
     *
     * @param request     검색 조건이 포함된 요청 객체
     * @param pageRequest 페이지네이션 정보
     * @return 검색 조건에 맞는 FAQ 목록
     */
    private Page<FaqResponse> findFaqs(FaqSearchRequest request, PageRequest pageRequest) {
        Page<Faq> faqs;

        if (request.query() != null && !request.query().isEmpty()) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);
        } else if (request.category() != null && request.subcategory() != null) {
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);
        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
        }

        return faqs.map(FaqResponse::fromEntity);
    }
}
