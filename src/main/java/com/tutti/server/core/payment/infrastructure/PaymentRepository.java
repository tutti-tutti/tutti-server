package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);
}
