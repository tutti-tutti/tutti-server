package com.tutti.server.core.faq.infrastructure;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

    default Faq findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.FAQ_NOT_FOUND));
    }

    /**
     * 특정 카테고리 및 서브카테고리의 FAQ 목록 조회 (삭제되지 않고 isView = true인 데이터만)
     */
    Page<Faq> findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
            String mainCategory, String subCategory, Pageable pageable);

    /**
     * 특정 키워드를 포함하는 FAQ 목록 조회 (삭제되지 않고 isView = true인 데이터만)
     */
    Page<Faq> findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
            String query, Pageable pageable);

    /**
     * 전체 FAQ 목록 조회 (삭제되지 않고 isView = true인 데이터만)
     */
    Page<Faq> findByDeleteStatusFalseAndIsViewTrue(Pageable pageable);

    /**
     * 인기 FAQ 목록 조회 (삭제되지 않고 isView = true인 데이터만, 조회수 기준 정렬)
     */
    @Query("SELECT f FROM Faq f WHERE f.deleteStatus = false AND f.isView = true ORDER BY f.viewCnt DESC")
    List<Faq> findTopFaqs(Pageable pageable);

    /**
     * 특정 ID의 FAQ 조회 (삭제되지 않고 isView = true인 데이터만)
     */
    @Query("SELECT f FROM Faq f WHERE f.id = :faqId AND f.deleteStatus = false AND f.isView = true")
    Optional<Faq> findByIdAndDeleteStatusFalseAndIsViewTrue(@Param("faqId") Long faqId);

    /**
     * 조회수 증가 (UPDATE 쿼리 실행)
     */
    @Modifying
    @Query("UPDATE Faq f SET f.viewCnt = f.viewCnt + 1 WHERE f.id = :faqId")
    void incrementViewCount(@Param("faqId") Long faqId);
}
