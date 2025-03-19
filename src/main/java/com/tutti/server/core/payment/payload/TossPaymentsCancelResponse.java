package com.tutti.server.core.payment.payload;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public record TossPaymentsCancelResponse(
        String status,
        LocalDateTime canceledAt

) {

    public static TossPaymentsCancelResponse fromResponse(Map<String, Object> response) {
        String status = (String) response.get("status");

        LocalDateTime canceledAt = ((List<?>) response.getOrDefault("cancels", List.of())).stream()
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(cancelMap -> cancelMap.get("canceledAt"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(canceledAtStr -> OffsetDateTime.parse(canceledAtStr,
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .map(OffsetDateTime::toLocalDateTime)
                .findFirst()
                .orElse(null);

        return new TossPaymentsCancelResponse(status, canceledAt);
    }

}
