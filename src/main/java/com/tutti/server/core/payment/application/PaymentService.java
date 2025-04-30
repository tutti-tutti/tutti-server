package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.request.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.request.PaymentRequest;
import com.tutti.server.core.payment.payload.response.PaymentConfirmResponse;
import com.tutti.server.core.payment.payload.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse requestPayment(PaymentRequest request, Long memberId);

    PaymentConfirmResponse confirmPayment(PaymentConfirmRequest request, Long AuthMemberId);

}
