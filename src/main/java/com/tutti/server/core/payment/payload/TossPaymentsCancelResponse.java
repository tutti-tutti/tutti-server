package com.tutti.server.core.payment.payload;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public record TossPaymentsCancelResponse(
        String status,
        LocalDateTime canceledAt

) {

    public static TossPaymentsCancelResponse fromResponse(Map<String, Object> response) {
        String status = (String) response.get("status");
        String canceledAtStr = (String) response.get("canceledAt");

        LocalDateTime canceledAt = null;
        if (canceledAtStr != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(canceledAtStr,
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            canceledAt = offsetDateTime.toLocalDateTime();
        }

        return new TossPaymentsCancelResponse(
                status,
                canceledAt
        );
    }

}
