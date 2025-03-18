package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductCategoryMap;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryMapRepository extends JpaRepository<ProductCategoryMap, Long> {

    default ProductCategoryMap findOne(Long id) {
        return findById(id)
                .orElseThrow(
                        () -> new DomainException(ExceptionType.PRODUCT_CATEGORY_MAP_NOT_FOUND));
    }
}
