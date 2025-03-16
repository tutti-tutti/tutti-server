package com.tutti.server.core.faq.infrastructure;

import com.tutti.server.core.faq.domain.Faq;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
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

    Page<Faq> findByFaqCategory_MainCategoryAndFaqCategory_SubCategoryAndDeleteStatusFalseAndIsViewTrue(
        String mainCategory, String subCategory, Pageable pageable);

    Page<Faq> findByQuestionContainingIgnoreCaseAndDeleteStatusFalseAndIsViewTrue(
        String query, Pageable pageable);

    Page<Faq> findByDeleteStatusFalseAndIsViewTrue(Pageable pageable);

    @Query("SELECT f FROM Faq f WHERE f.deleteStatus = false AND f.isView = true ORDER BY f.viewCnt DESC")
    List<Faq> findTopFaqs(Pageable pageable);

    @Modifying
    @Query("UPDATE Faq f SET f.viewCnt = f.viewCnt + 1 WHERE f.id = :faqId")
    void incrementViewCount(@Param("faqId") Long faqId);

    default List<Faq> findTopFaqsOrThrow(Pageable pageable) {
        List<Faq> faqs = findTopFaqs(pageable);
        if (faqs.isEmpty()) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
        return faqs;
    }

    @Modifying
    @Query("UPDATE Faq f SET f.viewCnt = f.viewCnt + 1 WHERE f.id = :faqId")
    default void incrementViewCountOrThrow(@Param("faqId") Long faqId) {
        if (!existsById(faqId)) {
            throw new DomainException(ExceptionType.FAQ_NOT_FOUND);
        }
        incrementViewCount(faqId);
    }
}
