package com.tutti.server.core.faq.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "카테고리")
public record FaqCategoryResponse(
    @Schema String mainCategory,
    @Schema List<String> subCategories
) {

}
