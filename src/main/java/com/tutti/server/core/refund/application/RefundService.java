package com.tutti.server.core.refund.application;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.refund.payload.RefundViewResponse;

public interface RefundService {

    void requestRefund(PaymentCancelRequest request);

    RefundViewResponse getRefundView(Long orderId);
}
