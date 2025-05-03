package com.tutti.server.core.tag.payload.response;

import lombok.Builder;

@Builder
public record ProductTagResponse(
        Long tagId,
        String tagName
) {

}
