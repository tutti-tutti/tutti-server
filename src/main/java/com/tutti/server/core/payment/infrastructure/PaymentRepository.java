package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    default Payment findOne(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
    }

    default Payment findPaymentByOrderId(Long orderId) {
        return findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
    }

    Optional<Payment> findByIdAndMemberId(Long paymentId, Long memberId);

    Optional<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByOrderNumberAndMemberId(String orderNumber, Long memberId);
}
