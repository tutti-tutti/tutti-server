package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.application.PaymentViewService;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentApi {

    private final PaymentService paymentService;
    private final PaymentViewService paymentViewService;

    @PostMapping("/request")
    public PaymentResponse requestPayment(
            @Valid @RequestBody PaymentRequest request) {

        return paymentService.requestPayment(request);
    }

    // 회원ID로 결제 조회
    @GetMapping("/memberId/{memberId}")
    public List<PaymentViewResponse> getMemberPayments(@PathVariable Long memberId) {

        return paymentViewService.viewPaymentsByMemberId(memberId);
    }

    @GetMapping("/orderId/{orderId}")
    public PaymentViewResponse getPaymentsViewOrderId(@PathVariable Long orderId) {

        return paymentViewService.getPaymentByOrderId(orderId);
    }

}
