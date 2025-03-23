package com.tutti.server.core.payment.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.order.payload.request.OrderCreateRequest;
import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.request.PaymentConfirmRequest;
import com.tutti.server.core.payment.payload.response.PaymentViewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payments", description = "결제 관련 API")
public interface PaymentApiSpec {

    @Operation(summary = "주문/결제 요청", description = "사용자가 주문을 완료하고, 결제를 요청하는 API")
    void requestPayment(OrderCreateRequest request, CustomUserDetails user);

    @Operation(summary = "결제 승인", description = "사용자가 결제 승인을 요청하는 API")
    void confirmPayment(PaymentConfirmRequest request, CustomUserDetails user);

    @Operation(summary = "결제 취소", description = "사용자가 결제를 취소하는 API")
    void cancelPayment(PaymentCancelRequest request, CustomUserDetails user);

    @Operation(summary = "paymentId로 결제 조회", description = "특정 회원 ID를 기반으로 결제 내역을 조회하는 API")
    PaymentViewResponse getPaymentIdViewPayments(Long paymentId, CustomUserDetails user);

    @Operation(summary = "주문 ID로 결제 조회", description = "특정 주문 ID를 기반으로 결제 내역을 조회하는 API")
    PaymentViewResponse getPaymentsViewOrderId(Long orderId, CustomUserDetails user);

}
