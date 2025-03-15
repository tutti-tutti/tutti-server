package com.tutti.server.core.payment.infrastructure;

import com.tutti.server.core.payment.domain.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {


}
