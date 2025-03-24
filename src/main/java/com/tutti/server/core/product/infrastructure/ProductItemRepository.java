package com.tutti.server.core.product.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    default ProductItem findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));
    }
    
    List<ProductItem> findByProduct(Product product);

    @Query("SELECT pi FROM ProductItem pi " +
           "WHERE pi.product.id = :productId " +
           "AND pi.deleteStatus = false " +
           "ORDER BY pi.sellingPrice ASC")
    List<ProductItem> findByProductIdOrderBySellingPriceAsc(@Param("productId") Long productId);

    Optional<ProductItem> findFirstByProductIdOrderBySellingPriceAsc(Long productId);

    @Query("SELECT pi FROM ProductItem pi " +
           "WHERE pi.product.id = :productId " +
           "AND pi.deleteStatus = false")
    List<ProductItem> findActiveItemsByProductId(@Param("productId") Long productId);

}
