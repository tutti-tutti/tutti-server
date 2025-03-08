package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentConfirmRequest;

import java.util.Map;

public interface PaymentService {

    Map<String, Object> confirmPayment(PaymentConfirmRequest request);
}
