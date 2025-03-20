package com.tutti.server.core.returns.api;

import com.tutti.server.core.returns.payload.ReturnsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "반품신청 API", description = "반품 신청을 처리하는 API")
public interface ReturnsApiSpec {

    @Operation(summary = "반품 요청 API", description = "사용자가 특정 주문의 반품을 요청합니다.")
    ResponseEntity<String> requestReturns(ReturnsRequest request);

}
