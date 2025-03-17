package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import java.util.Map;

public interface PaymentService {

    PaymentResponse requestPayment(PaymentRequest request);

    Map<String, Object> confirmPayment(PaymentConfirmRequest request);

    void paymentCancel(PaymentCancelRequest request);

}
