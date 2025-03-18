package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import org.springframework.stereotype.Component;

@Component
public interface PaymentCancelService {

    void paymentCancel(PaymentCancelRequest request);

}
