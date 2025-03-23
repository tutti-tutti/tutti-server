package com.tutti.server.core.refund.application;

import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.refund.payload.RefundViewResponse;

public interface RefundService {

    void requestRefund(PaymentCancelRequest request, Long AuthMemberId);

    RefundViewResponse getRefundView(Long orderId, Long AuthMemberId);
}
