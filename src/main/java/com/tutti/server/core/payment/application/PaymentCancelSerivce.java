package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;

public interface PaymentCancelSerivce {

    void paymentCancel(PaymentCancelRequest request);

}
