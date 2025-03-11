package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
