package com.tutti.server.core.refund.api;

import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.refund.application.RefundService;
import com.tutti.server.core.refund.payload.RefundViewResponse;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500") // 프론트엔드 주소
@RestController
@RequiredArgsConstructor
@RequestMapping("/refund")
public class RefundApi implements RefundApiSpec {

    private final RefundService refundService;

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> requestRefund(
            @Valid @RequestBody PaymentCancelRequest request) {
        refundService.requestRefund(request);
        return ResponseEntity.ok(Map.of("message", "결제가 취소되었습니다."));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<RefundViewResponse> getRefundViewByOrderId(@PathVariable Long orderId) {
        if (orderId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(refundService.getRefundView(orderId));
    }
}
