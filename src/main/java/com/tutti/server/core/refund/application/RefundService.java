package com.tutti.server.core.refund.application;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;

public interface RefundService {

    void requestRefund(PaymentCancelRequest request);
}
