package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;

public interface PaymentCancelService {

    Payment paymentCancel(PaymentCancelRequest request, Long AuthMemberId);
}
