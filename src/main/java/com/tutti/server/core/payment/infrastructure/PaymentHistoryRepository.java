package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.payment.domain.PaymentHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    // paymentId와 latestStatus가 true인 이력 조회
    Optional<PaymentHistory> findByPaymentIdAndLatestStatus(Long paymentId, boolean latestStatus);

}
