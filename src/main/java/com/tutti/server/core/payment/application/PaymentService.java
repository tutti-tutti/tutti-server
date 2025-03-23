package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.request.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.request.PaymentRequest;
import com.tutti.server.core.payment.payload.response.PaymentResponse;
import java.util.Map;

public interface PaymentService {

    PaymentResponse requestPayment(PaymentRequest request, Long memberId);

    Map<String, Object> confirmPayment(PaymentConfirmRequest request, Long AuthMemberId);

}
