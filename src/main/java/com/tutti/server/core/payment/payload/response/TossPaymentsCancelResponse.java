package com.tutti.server.core.payment.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;
import java.util.List;

public record TossPaymentsCancelResponse(
        String status,
        List<CancelDetail> cancels
) {

    public record CancelDetail(
            String cancelReason,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            OffsetDateTime canceledAt,

            int cancelAmount,
            int taxFreeAmount
    ) {

    }
}
