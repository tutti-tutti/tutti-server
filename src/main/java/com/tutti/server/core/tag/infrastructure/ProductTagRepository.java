package com.tutti.server.core.tag.infrastructure;

import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import com.tutti.server.core.tag.domain.ProductTag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    default ProductTag findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_TAG_NOT_FOUND));
    }

    Optional<ProductTag> findAllByProductIdAndDeleteStatusFalse(Long productId);
}
