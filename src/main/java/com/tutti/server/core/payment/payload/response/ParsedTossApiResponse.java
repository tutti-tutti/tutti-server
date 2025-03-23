package com.tutti.server.core.payment.payload.response;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public record ParsedTossApiResponse(
        String paymentKey,  //
        String status,      // 결제 상태 (예: SUCCESS, FAILED)
        String method,      // 결제 방법 (예: 카드, 가상계좌 등)
        LocalDateTime approvedAt, // 결제 승인 시간
        int amount          // 결제 금액
) {

    public static ParsedTossApiResponse fromResponse(Map<String, Object> response) {
        String paymentKey = (String) response.get("paymentKey");
        String status = (String) response.get("status");
        String method = (String) response.get("method");
        String approvedAtStr = (String) response.get("approvedAt");
        Integer totalAmount = (Integer) response.get("totalAmount");

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(approvedAtStr,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDateTime approvedAt = offsetDateTime.toLocalDateTime();

        return new ParsedTossApiResponse(
                paymentKey,
                status,
                method,
                approvedAt,
                (totalAmount != null) ? totalAmount : 0
        );
    }

}
