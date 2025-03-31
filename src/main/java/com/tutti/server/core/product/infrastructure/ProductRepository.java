package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));
    }

    @Query("SELECT p FROM Product p JOIN ProductCategoryMap pcm ON p = pcm.product " +
            "WHERE pcm.category.id = :categoryId AND pcm.deleteStatus = false")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Product p WHERE p.id < :lastId ORDER BY p.createdAt DESC, p.id DESC")
    Slice<Product> findAllByIdLessThanOrderByCreatedAtDescIdDesc(@Param("lastId") Long lastId,
            Pageable pageable);

}
