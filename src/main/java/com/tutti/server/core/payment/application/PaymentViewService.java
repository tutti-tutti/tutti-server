package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.response.PaymentViewResponse;

public interface PaymentViewService {

    PaymentViewResponse viewPaymentByMemberId(Long paymentId, Long AuthMemberId);

    PaymentViewResponse viewPaymentByOrderId(Long orderId, Long AuthMemberId);
}
