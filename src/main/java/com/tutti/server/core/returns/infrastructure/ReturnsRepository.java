package com.tutti.server.core.returns.infrastructure;

import com.tutti.server.core.returns.domain.Returns;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReturnsRepository extends JpaRepository<Returns, Long> {

    @Query("SELECT r FROM Returns r WHERE r.order.id = :orderId")
    Optional<Returns> findByOrderId(@Param("orderId") Long orderId);

}
