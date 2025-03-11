package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.payment.domain.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTossPaymentKey(String tossPaymentKey);
}
