package com.tutti.server.core.returns.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.returns.payload.ReturnsRequest;
import com.tutti.server.core.returns.payload.ReturnsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "반품 API", description = "반품 신청을 처리하는 API")
public interface ReturnsApiSpec {

    @Operation(summary = "반품 요청 API", description = "사용자가 특정 주문의 반품을 요청합니다.")
    ResponseEntity<String> requestReturns(ReturnsRequest request, @AuthenticationPrincipal
    CustomUserDetails user);

    @Operation(summary = "주문 ID로 반품 조회 API", description = "특정 주문에 대한 반품 정보를 조회합니다.")
    ReturnsResponse getReturnsByOrderId(Long orderId, @AuthenticationPrincipal
    CustomUserDetails user);
}
