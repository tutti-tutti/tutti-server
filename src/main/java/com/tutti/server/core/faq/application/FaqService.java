package com.tutti.server.core.faq.application;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.faq.infrastructure.FaqCategoryRepository;
import com.tutti.server.core.faq.infrastructure.FaqRepository;
import com.tutti.server.core.faq.payload.request.FaqFeedbackRequest;
import com.tutti.server.core.faq.payload.request.FaqRequest;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import com.tutti.server.core.faq.payload.response.FaqFeedbackResponse;
import com.tutti.server.core.faq.payload.response.FaqListResponse;
import com.tutti.server.core.faq.payload.response.FaqResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final FaqCategoryRepository faqCategoryRepository;

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
            .map(this::mapToFaqResponse)
            .collect(Collectors.toList());

        return FaqListResponse.builder()
            .totalResults((int) faqs.getTotalElements())
            .currentPage(page)
            .size(size)
            .results(faqResponses)
            .build();
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

    /**
     * FAQ 단건 조회 (삭제되지 않고 isView가 true인 데이터만)
     */
    @Transactional(readOnly = true)
    public FaqResponse getFaqById(Long faqId) {
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new IllegalArgumentException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        // 조회수 증가
        faq.incrementViewCount();
        faqRepository.save(faq);

        return mapToFaqResponse(faq);
    }

    /**
     * FAQ 등록
     */
    @Transactional
    public FaqResponse createFaq(FaqRequest request) {
        FaqCategory faqCategory = faqCategoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException(
                "해당 카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));

        Faq faq = faqRepository.save(Faq.builder()
            .faqCategory(faqCategory)
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .isView(request.getIsView())
            .build());

        return mapToFaqResponse(faq);
    }

    /**
     * FAQ 수정
     */
    @Transactional
    public FaqResponse updateFaq(Long faqId, @Valid FaqRequest request) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new IllegalArgumentException("해당 FAQ를 찾을 수 없습니다. ID: " + faqId));

        if (request.getCategoryId() != null) {
            FaqCategory faqCategory = faqCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "해당 카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));
            faq.updateCategory(faqCategory);
        }

        faq.updateFaq(request.getQuestion(), request.getAnswer(), request.getIsView());
        return mapToFaqResponse(faq);
    }

    /**
     * FAQ 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new IllegalArgumentException("해당 FAQ를 찾을 수 없습니다. ID: " + faqId));

        faq.markAsDeleted(); // deleteStatus = true, isView = false 변경
        faqRepository.save(faq);
    }

    /**
     * FAQ 조회수 증가
     */
    @Transactional
    public long incrementViewCount(Long faqId) {
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new IllegalArgumentException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        faq.incrementViewCount();
        faqRepository.save(faq);

        return faq.getViewCnt();
    }


    /**
     * FAQ 검색 (삭제되지 않고 isView가 true인 데이터만)
     */
    @Transactional(readOnly = true)
    public FaqListResponse searchFaqs(String query, int page, int size) {
        Page<Faq> faqs = faqRepository.findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
            query, PageRequest.of(page - 1, size));

        List<FaqResponse> faqResponses = faqs.getContent().stream()
            .map(this::mapToFaqResponse)
            .collect(Collectors.toList());

        return FaqListResponse.builder()
            .totalResults((int) faqs.getTotalElements())
            .currentPage(page)
            .size(size)
            .results(faqResponses)
            .build();
    }

    /**
     * FAQ 피드백 기능
     */
    @Transactional
    public FaqFeedbackResponse updateFeedback(Long faqId, FaqFeedbackRequest request) {
        Faq faq = faqRepository.findByIdAndDeleteStatusFalseAndIsViewTrue(faqId)
            .orElseThrow(() -> new IllegalArgumentException("FAQ를 찾을 수 없습니다. ID: " + faqId));

        if (request.getPositive()) {
            faq.incrementPositive();
        } else {
            faq.incrementNegative();
        }

        faqRepository.save(faq);

        return FaqFeedbackResponse.builder()
            .message("피드백이 반영되었습니다.")
            .positive(faq.getPositive())
            .negative(faq.getNegative())
            .build();
    }

    private FaqResponse mapToFaqResponse(Faq faq) {
        return FaqResponse.builder()
            .id(faq.getId())
            .faqCategory(FaqCategoryResponse.builder()
                .id(faq.getFaqCategory().getId())
                .mainCategory(faq.getFaqCategory().getMainCategory())
                .subCategory(faq.getFaqCategory().getSubCategory())
                .build())
            .question(faq.getQuestion())
            .answer(faq.getAnswer())
            .build();
    }

    public List<String> getCategories() {
        return faqCategoryRepository.findDistinctMainCategories();
    }

    public List<FaqCategoryResponse> getCategoryList() {
        return faqCategoryRepository.findAllCategoriesAsDto();
    }
}
