package com.tutti.server.core.faq.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record FaqSearchRequest(
    @Parameter(description = "검색 키워드", example = "쿠폰") String query,
    @Parameter(description = "페이지 번호 (기본값: 1)", example = "1") @DefaultValue("1") int page,
    @Parameter(description = "페이지당 데이터 개수 (기본값: 20)", example = "20") @DefaultValue("20") int size
) {

}
