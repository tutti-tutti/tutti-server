package com.tutti.server.core.refund.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.refund.application.RefundService;
import com.tutti.server.core.refund.payload.RefundViewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void requestRefund(
            @Valid @RequestBody PaymentCancelRequest request) {
        refundService.requestRefund(request);
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<RefundViewResponse> getRefundViewByOrderId(@PathVariable Long orderId,
            @AuthenticationPrincipal
            CustomUserDetails userDetails) {
        long memberId = userDetails.getMemberId();
        return ResponseEntity.ok(refundService.getRefundView(orderId, memberId));
    }
}
