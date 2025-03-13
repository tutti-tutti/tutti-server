package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import java.util.Map;

public interface PaymentConfirmService {

    Map<String, Object> confirmPayment(PaymentConfirmRequest request);
    
}
