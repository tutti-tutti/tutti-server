package com.tutti.server.core.order.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderPageRequest(

        @NotNull
        @Schema(description = "주문할 상품 목록",
                example = """
                        [
                            {
                                "productItemId": 72,
                                "quantity": 1,
                            },
                            {
                                "productItemId": 196,
                                "quantity": 1,
                            }
                        ]
                        """
        )
        List<OrderItemRequest> orderItems
) {

}
