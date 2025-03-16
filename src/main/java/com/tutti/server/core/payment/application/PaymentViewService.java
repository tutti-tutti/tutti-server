package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.payload.PaymentViewResponse;
import com.tutti.server.core.payment.payload.ViewMemberIdRequest;
import com.tutti.server.core.payment.payload.ViewOrderIdRequest;
import java.util.List;

public interface PaymentViewService {

    List<PaymentViewResponse> viewPaymentsByMemberId(ViewMemberIdRequest memberId);

    PaymentViewResponse viewPaymentByOrderId(ViewOrderIdRequest orderId);
}
