package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.PaymentStatus;

public record PaymentResponse(Long paymentId, PaymentStatus paymentStatus) {
}
