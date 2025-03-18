package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentHistory;
import com.tutti.server.core.payment.infrastructure.PaymentHistoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    @Override
    public void savePaymentHistory(Payment payment) {
        // 기존 이력 상태를 최신(false)으로 변경
        paymentHistoryRepository.findByPaymentIdAndLatestStatus(payment.getId(), true)
                .ifPresent(oldHistory -> {
                    oldHistory.markAsOldHistory();
                    paymentHistoryRepository.save(oldHistory);
                });

        // 새 이력 저장
        PaymentHistory paymentHistory = PaymentHistory.builder()
                .payment(payment)
                .paymentStatus(payment.getPaymentStatus())
                .latestStatus(true)
                .statusUpdatedAt(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(paymentHistory);
    }
}
