package com.tutti.server.core.faq.infrastructure;

import com.tutti.server.core.faq.domain.Faq;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

    // 특정 카테고리의 FAQ 목록 조회 (삭제되지 않고, isView = true인 데이터만)
    Page<Faq> findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
        String mainCategory, String subCategory, Pageable pageable);

    // 특정 키워드를 포함하는 FAQ 목록 조회 (삭제되지 않고, isView = true인 데이터만)
    Page<Faq> findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
        String query, Pageable pageable);

    // 전체 FAQ 조회 (삭제되지 않고, isView = true인 데이터만)
    Page<Faq> findByDeleteStatusFalseAndIsViewTrue(Pageable pageable);

    // 인기 FAQ 조회 (삭제되지 않고, isView = true인 데이터만, 조회수 기준 정렬)
    @Query("SELECT f FROM Faq f WHERE f.deleteStatus = false AND f.isView = true ORDER BY f.viewCnt DESC")
    List<Faq> findTopFaqs(Pageable pageable);

    // 특정 ID의 단일 FAQ 조회 (삭제되지 않고, isView = true인 데이터만)
    @Query("SELECT f FROM Faq f WHERE f.id = :faqId AND f.deleteStatus = false AND f.isView = true")
    Optional<Faq> findByIdAndDeleteStatusFalseAndIsViewTrue(Long faqId);
}
