package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;

public interface PaymentHistoryService {

    void savePaymentHistory(Payment payment);
}
