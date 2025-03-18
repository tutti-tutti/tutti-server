package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentCancelService;
import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.application.PaymentViewService;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/payments")
public class PaymentApi {

    private final PaymentService paymentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PaymentCancelService paymentCancelService;

    private final PaymentViewService paymentViewService;

    @PostMapping("/request")
    public PaymentResponse requestPayment(
            @Valid @RequestBody PaymentRequest request) {

        return paymentService.requestPayment(request);
    }

    @PostMapping("/confirm/success")
    public ResponseEntity<Map<String, String>> confirmPayment(
            @Valid @RequestBody PaymentConfirmRequest request) {
        paymentService.confirmPayment(request);
        Map<String, String> body = Map.of("message", "결제가 완료되었습니다.");
        return ResponseEntity.ok(body);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPayment(
            @Valid @RequestBody PaymentCancelRequest request) {
        logger.info("결제 취소 요청: 주문 ID = {}", request.orderId());
        paymentCancelService.paymentCancel(request);
        return ResponseEntity.ok(Map.of("message", "결제가 취소되었습니다."));
    }

    // 회원ID로 결제 조회
    @GetMapping("/memberId/{memberId}")
    public List<PaymentViewResponse> getMemberPayments(@PathVariable Long memberId) {

        return paymentViewService.viewPaymentByMemberId(memberId);
    }

    // 주문ID로 결제 조회
    @GetMapping("/orderId/{orderId}")
    public PaymentViewResponse getPaymentsViewOrderId(@PathVariable Long orderId) {

        return paymentViewService.viewPaymentByOrderId(orderId);
    }

}
