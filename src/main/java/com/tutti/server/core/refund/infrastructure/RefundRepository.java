package com.tutti.server.core.refund.infrastructure;

import com.tutti.server.core.refund.domain.Refund;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByPaymentOrderId(Long orderId);

}
