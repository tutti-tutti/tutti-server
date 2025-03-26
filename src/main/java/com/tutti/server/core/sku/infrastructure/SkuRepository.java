package com.tutti.server.core.sku.infrastructure;

import com.tutti.server.core.sku.domain.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {

//    @Query("SELECT s FROM Sku s WHERE s.productItem IN :productItems")
//    List<Sku> findByProductItems(@Param("productItems") List<ProductItem> productItems);
}
