package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    default ProductItem findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));
    }

    Optional<ProductItem> findFirstByProductIdOrderBySellingPriceAsc(Long productId);

    @Query("SELECT pi FROM ProductItem pi " +
            "WHERE pi.product.id = :productId " +
            "AND pi.deleteStatus = false")
    List<ProductItem> findActiveItemsByProductId(@Param("productId") Long productId);

}
