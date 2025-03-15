package com.tutti.server.core.payment.api;

import com.tutti.server.core.payment.application.PaymentViewByMemberService;
import com.tutti.server.core.payment.application.PaymentViewByOrderIdService;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments/view")
public class PaymentViewApi {

    private final PaymentViewByMemberService paymentViewService;
    private final PaymentViewByOrderIdService paymentViewByOrderIdService;

    // 회원ID로 결제 조회
    @GetMapping("/memberId/{memberId}")
    public List<PaymentViewResponse> getMemberPayments(@PathVariable Long memberId) {

        return paymentViewService.viewPaymentsByMemberId(memberId);
    }

    @GetMapping("/orderId/{orderId}")
    public PaymentViewResponse getPaymentsViewOrderId(@PathVariable Long orderId) {

        return paymentViewByOrderIdService.getPaymentByOrderId(orderId);
    }

}
