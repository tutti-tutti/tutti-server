package com.tutti.server.core.faq.infrastructure;

import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long> {

    default FaqCategory findOne(Long id) {
        return findById(id)
            .orElseThrow(() -> new DomainException(ExceptionType.FAQ_CATEGORY_NOT_FOUND));
    }

    @Query("SELECT DISTINCT f.mainCategory FROM FaqCategory f")
    List<String> findDistinctMainCategories();

    @Query("SELECT DISTINCT f.subCategory FROM FaqCategory f WHERE f.mainCategory = :mainCategory")
    List<String> findDistinctSubCategoriesByMainCategory(
        @Param("mainCategory") String mainCategory);

}
