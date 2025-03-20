package com.tutti.server.core.returns.payload;

import com.tutti.server.core.returns.domain.Returns;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReturnsResponse(

        Long orderId,
        String returnsStatus,
        LocalDateTime completedAt
) {

    public static ReturnsResponse fromEntity(Returns returns) {

        return ReturnsResponse.builder()
                .orderId(returns.getOrder().getId())
                .returnsStatus(returns.getReturnStatus().name())
                .completedAt(returns.getCompletedAt())
                .build();
    }

}
