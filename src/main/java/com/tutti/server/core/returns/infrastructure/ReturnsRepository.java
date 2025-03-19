package com.tutti.server.core.returns.infrastructure;

import com.tutti.server.core.returns.domain.Returns;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnsRepository extends JpaRepository<Returns, Long> {

    Optional<Returns> findByOrderId(Long orderId);

}
