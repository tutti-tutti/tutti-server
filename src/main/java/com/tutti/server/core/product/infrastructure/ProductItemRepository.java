package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
