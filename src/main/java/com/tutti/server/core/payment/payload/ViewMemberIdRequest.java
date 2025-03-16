package com.tutti.server.core.payment.payload;

import jakarta.validation.constraints.NotNull;

public record ViewMemberIdRequest(
        @NotNull Long memberId
) {

}
