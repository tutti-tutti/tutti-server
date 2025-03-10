package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentResponse(Long paymentId,
                              PaymentStatus paymentStatus,
                              String orderName,
                              int amount,
                              Long orderId,
                              LocalDateTime createdAt) {

}
