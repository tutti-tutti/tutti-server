package com.tutti.server.core.faq.infrastructure;

import com.tutti.server.core.faq.domain.FaqCategory;
import com.tutti.server.core.faq.payload.response.FaqCategoryResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long> {

    // 메인 카테고리 목록만 조회 (중복 제거)
    @Query("SELECT DISTINCT f.mainCategory FROM FaqCategory f")
    List<String> findDistinctMainCategories();

    // FaqCategoryResponse DTO로 조회
    @Query("SELECT new com.tutti.server.core.faq.payload.response.FaqCategoryResponse(f.id, f.mainCategory, f.subCategory) FROM FaqCategory f")
    List<FaqCategoryResponse> findAllCategoriesAsDto();
}
