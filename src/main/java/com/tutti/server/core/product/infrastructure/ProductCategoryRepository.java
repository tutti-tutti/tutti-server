package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductCategory;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    default ProductCategory findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.CATEGORY_NOT_FOUND));
    }
}
