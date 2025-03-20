package com.tutti.server.core.refund.api;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.refund.payload.RefundViewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "환불 API", description = "환불 요청을 처리하는 API")
public interface RefundApiSpec {

    @Operation(summary = "환불 요청", description = "사용자가 환불을 요청하는 API(환불 요청시 결제취소 프로세스도 실행)")
    ResponseEntity<Map<String, String>> requestRefund(PaymentCancelRequest request);

    @Operation(summary = "주분번호로 환불 조회", description = "사용자가 환불의 진행상태를 조회하기 위한 API")
    ResponseEntity<RefundViewResponse> getRefundViewByOrderId(Long orderId);

}
