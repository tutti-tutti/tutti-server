package com.tutti.server.core.refund.api;

import com.tutti.server.core.refund.payload.RefundRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Tag(name = "환불 API", description = "환불 요청을 처리하는 API")
public interface RefundApiSpec {

    @Operation(summary = "환불 요청", description = "사용자가 환불을 요청하는 API")
    ResponseEntity<Map<String, String>> requestRefund(RefundRequest request);
    
}
