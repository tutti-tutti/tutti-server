package com.tutti.server.core.refund.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefundRequest(

        @NotNull
        Long orderId,

        @NotBlank
        String reason
) {

}
