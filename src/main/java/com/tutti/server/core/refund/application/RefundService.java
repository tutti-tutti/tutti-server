package com.tutti.server.core.refund.application;

import com.tutti.server.core.refund.payload.RefundRequest;

public interface RefundService {

    void requestRefund(RefundRequest request);
}
