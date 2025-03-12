package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqListRequest;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import com.tutti.server.core.faq.payload.response.FaqSearchResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FAQ 관련 비즈니스 로직을 처리하는 서비스 클래스입니다. FAQ 목록 조회, 검색, 조회수 증가 등의 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqCategoryRepository faqCategoryRepository;
    private final FaqRepository faqRepository;

    /**
     * FAQ 카테고리 목록을 조회합니다.
     *
     * @return 중복이 제거된 FAQ 카테고리 목록
     */
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }

    /**
     * 검색 조건을 포함한 FAQ 목록을 조회합니다.
     *
     * @param request 검색 조건을 포함한 요청 객체
     * @return 검색 조건에 맞는 FAQ 목록
     */
    @Transactional(readOnly = true)
    public FaqListResponse getFaqs(FaqListRequest request) {
        // 페이지 정보 설정
        PageRequest pageRequest = PageRequest.of(request.page() - 1, request.size());
        Page<FaqResponse> faqResponses = findFaqs(request, pageRequest);

        return new FaqListResponse(
            (int) faqResponses.getTotalElements(),
            request.page(),
            request.size(),
            new FaqSearchResponse(faqResponses.getContent())
        );
    }

    /**
     * 조회수가 높은 상위 N개의 FAQ를 조회합니다.
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
     * 특정 FAQ를 조회하고 조회수를 증가시킵니다. 비동기 처리로 조회수를 증가시키며, 실패 시 예외를 발생시킵니다.
     *
     * @param faqId 조회할 FAQ ID
     * @return 조회된 FAQ 정보
     * @throws EntityNotFoundException FAQ가 존재하지 않으면 예외를 발생시킴
     */
    @Transactional
    public FaqResponse getFaqById(Long faqId) {
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new EntityNotFoundException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        incrementViewCount(faqId); // 조회수 증가 (비동기 실행)

        return FaqResponse.fromEntity(faq);
    }

    /**
     * FAQ 조회수를 비동기적으로 증가시킵니다. 새로운 트랜잭션에서 실행되며, 기존 요청과 독립적으로 실행됩니다.
     *
     * @param faqId 조회수 증가 대상 FAQ ID
     */
    @Async
    @Transactional
    public void incrementViewCount(Long faqId) {
        faqRepository.incrementViewCount(faqId);
    }

    /**
     * 주어진 검색 조건을 기반으로 FAQ 목록을 조회합니다.
     *
     * @param request     검색 조건을 포함한 요청 객체
     * @param pageRequest 페이지네이션 정보
     * @return 검색 조건에 맞는 FAQ 목록
     */
    private Page<FaqResponse> findFaqs(FaqListRequest request, PageRequest pageRequest) {
        Page<Faq> faqs;

        // 검색어가 있는 경우 검색어로 조회
        if (request.query() != null && !request.query().isEmpty()) {
            faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
                request.query(), pageRequest);
            // 카테고리와 서브카테고리가 있는 경우 카테고리로 조회
        } else if (request.category() != null && request.subcategory() != null) {
            faqs = faqRepository.findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
                request.category(), request.subcategory(), pageRequest);
            // 기본적으로 삭제되지 않고, 조회 가능한 FAQ 조회
        } else {
            faqs = faqRepository.findByDeleteStatusFalseAndIsViewTrue(pageRequest);
        }

        return faqs.map(FaqResponse::fromEntity);
    }

    /**
     * 주어진 검색어를 기반으로 FAQ 목록을 조회합니다.
     *
     * @param query 검색어
     * @param page  페이지 번호
     * @param size  페이지당 FAQ 개수
     * @return 검색 조건에 맞는 FAQ 목록
     */
    @Transactional(readOnly = true)
    public FaqListResponse searchFaqs(String query, int page, int size) {
        // 페이지 값이 1 미만이면 1로 설정
        if (page < 1) {
            page = 1;
        }

        // 검색어에 맞는 FAQ 목록 조회
        Page<Faq> faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
            query, PageRequest.of(page - 1, size));

        List<FaqResponse> faqResponses = faqs.getContent().stream()
            .map(FaqResponse::fromEntity)
            .toList();

        FaqSearchResponse faqSearchResponse = new FaqSearchResponse(faqResponses);

        return new FaqListResponse(
            (int) faqs.getTotalElements(),
            page,
            size,
            faqSearchResponse
        );
    }
}
