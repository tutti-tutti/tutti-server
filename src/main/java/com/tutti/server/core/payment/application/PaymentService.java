package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;

public interface PaymentService {

    PaymentResponse requestPayment(PaymentRequest request);
}
