package com.tutti.server.core.product.infrastructure;

import com.tutti.server.core.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Example, Long> {

}
