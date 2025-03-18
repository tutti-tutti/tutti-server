package com.tutti.server.core.refund.api;

import com.tutti.server.core.refund.payload.RefundRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "환불 API", description = "환불 요청을 처리하는 API")
public interface RefundApiSpec {

    @Operation(summary = "환불 요청", description = "사용자가 환불을 요청하는 API")
    ResponseEntity<Void> requestRefund(RefundRequest request);

//    @Operation(summary = "회원 ID로 환불 조회", description = "특정 회원 ID를 기반으로 환불 내역을 조회하는 API")
//    List<RefundViewResponse> getMemberRefunds(Long memberId);
//
//    @Operation(summary = "주문 ID로 환불 조회", description = "특정 주문 ID를 기반으로 환불 내역을 조회하는 API")
//    RefundViewResponse getRefundsByOrderId(Long orderId);
}
