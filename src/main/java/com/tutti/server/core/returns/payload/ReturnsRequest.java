package com.tutti.server.core.returns.payload;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.returns.domain.ReturnStatus;
import com.tutti.server.core.returns.domain.Returns;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReturnsRequest(

        @NotNull Long orderId,
        @NotNull String returnsReason
) {

    public Returns toEntity(Order order) {
        return Returns.builder()
                .order(order)
                .quantity(order.getOrderCount())
                .reason(this.returnsReason())
                .returnStatus(ReturnStatus.RETURN_COMPLETED)
                .expectedReturnDate(LocalDate.now().plusDays(3))
                .completedAt(LocalDateTime.now())
                .build();
    }

}
