package com.tutti.server.core.sku.infrastructure;

import com.tutti.server.core.product.domain.ProductItem;
import com.tutti.server.core.sku.domain.Sku;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkuRepository extends JpaRepository<Sku, Long> {

    @Query("SELECT s FROM Sku s WHERE s.productItem IN :productItems")
    List<Sku> findByProductItems(@Param("productItems") List<ProductItem> productItems);


    @Query("SELECT s FROM Sku s WHERE s.productItem.id IN :productItemIds")
    List<Sku> findByProductItemIds(@Param("productItemIds") List<Long> productItemIds);


}
