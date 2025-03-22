package com.tutti.server.core.order.payload.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderPageRequest(

        @NotNull
        List<OrderItemRequest> orderItems

) {

}
