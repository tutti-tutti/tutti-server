package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.payload.response.ProductResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN ProductCategoryMap pcm ON p = pcm.product " +
            "WHERE pcm.category.id = :categoryId AND pcm.deleteStatus = false")
    List<ProductResponse> findProductsByCategoryId(@Param("categoryId") Long categoryId);

}
