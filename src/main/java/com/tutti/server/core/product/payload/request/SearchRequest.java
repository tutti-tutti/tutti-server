package com.tutti.server.core.product.payload.request;

import lombok.Builder;

@Builder
public record SearchRequest(
        String keyword,
        Long cursorId,
        int size
) {
       
}
