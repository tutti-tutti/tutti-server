package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentViewResponse;

public interface PaymentViewByOrderIdService {

    PaymentViewResponse getPaymentByOrderId(Long orderId);

}
