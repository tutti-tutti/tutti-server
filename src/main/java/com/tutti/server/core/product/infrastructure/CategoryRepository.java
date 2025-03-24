package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductCategory;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

    default ProductCategory findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.CATEGORY_NOT_FOUND));
    }

    @Query("SELECT pc FROM ProductCategory pc LEFT JOIN FETCH pc.parentCategory ORDER BY pc.sortOrder")
    List<ProductCategory> findAllWithParent();

}
