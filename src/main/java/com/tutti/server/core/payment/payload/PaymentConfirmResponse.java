package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentConfirmResponse(
        String orderId,
        String orderName,
        int amount,
        PaymentStatus paymentStatus,
        PaymentMethodType paymentMethodType,
        LocalDateTime completedAt
) {

}
