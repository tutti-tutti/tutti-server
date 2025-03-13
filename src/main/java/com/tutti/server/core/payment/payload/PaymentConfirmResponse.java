package com.tutti.server.core.payment.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentConfirmResponse(
        @JsonProperty("orderId") String orderId,
        @JsonProperty("orderName") String orderName,
        @JsonProperty("amount") int amount,
        @JsonProperty("paymentStatus") PaymentStatus paymentStatus,
        @JsonProperty("paymentMethodType") PaymentMethodType paymentMethodType,
        @JsonProperty("completedAt") LocalDateTime completedAt
) {

}
