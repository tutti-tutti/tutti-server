package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentViewResponse;
import java.util.List;

public interface PaymentViewService {

    List<PaymentViewResponse> viewPaymentsByMemberId(Long memberId);

    PaymentViewResponse getPaymentByOrderId(Long orderId);


}
