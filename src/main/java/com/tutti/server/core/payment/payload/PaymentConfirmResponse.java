package com.tutti.server.core.payment.payload;

import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import java.time.LocalDateTime;
import java.util.Map;

public record PaymentConfirmResponse(String orderId, String orderName, int amount,
                                     PaymentStatus paymentStatus,
                                     PaymentMethodType paymentMethodType,
                                     LocalDateTime completedAt) {

    public static PaymentConfirmResponse from(Map<String, Object> response) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("응답이 없습니다.");
        }
        return new PaymentConfirmResponse(
                (String) response.get("orderId"),
                (String) response.get("orderName"),
                (int) response.get("amount"),
                PaymentStatus.fromString((String) response.get("status")),
                PaymentMethodType.fromString((String) response.get("method")),
                LocalDateTime.parse((String) response.get("completedAt"))
        );
    }
}
