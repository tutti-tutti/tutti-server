package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.TossPaymentsCancelResponse;
import com.tutti.server.core.refund.domain.RefundStatus;
import com.tutti.server.core.refund.infrastructure.RefundRepository;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentCancelServiceImpl implements PaymentCancelService {

    private final PaymentRepository paymentRepository;
    private final PaymentHistoryService paymentHistoryService;
    private final TossPaymentService tossPaymentService;
    private final RefundRepository refundRepository;

    @Override
    @Transactional
    public Payment paymentCancel(PaymentCancelRequest request) {
        // 결제 정보 조회 및 검증
        Payment payment = paymentRepository.findPaymentByOrderId(request.orderId());

        checkPaymentCancelEligibility(payment);

        // TossPayments API 호출 및 응답 처리
        TossPaymentsCancelResponse parsedResponse = tossPaymentService.cancelPayment(
                payment, request.cancelReason());

        // 결제 상태 업데이트 및 결제 이력 저장
        updatePaymentStatus(payment, parsedResponse);
        paymentHistoryService.savePaymentHistory(payment);

        return payment;
    }

    // 결제 상태 취소로 업데이트
    private void updatePaymentStatus(Payment payment, TossPaymentsCancelResponse response) {
        payment.cancelPayment(response.canceledAt());
        paymentRepository.save(payment);
    }

    // 결제 취소 시 확인(결제 상태(DONE), 환불상태(RefundNotCompleted), 주문상태(구매확정이면 불가)
    private void checkPaymentCancelEligibility(Payment payment) {
        if (payment.getPaymentStatus() != PaymentStatus.DONE) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_NOT_ALLOWED);
        }
        validateRefundPeriod(payment);
        validateRefundNotCompleted(payment.getOrder().getId());
//        validateOrderStatus(payment);

    }

    // 결제 취소 기간 검증(7일이 후면 자동 구매확정이므로)
    private void validateRefundPeriod(Payment payment) {
        LocalDateTime refundDeadline = payment.getCompletedAt().plusDays(7);
        if (LocalDateTime.now().isAfter(refundDeadline)) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_EXPIRED);
        }
    }

    // 환불 상태 확인.
    void validateRefundNotCompleted(Long orderId) {
        refundRepository.findByPaymentOrderId(orderId)
                .filter(refund -> refund.getRefundStatus() == RefundStatus.REFUND_COMPLETED)
                .ifPresent(refund -> {
                    throw new DomainException(ExceptionType.REFUND_ALREADY_COMPLETED);
                });
    }

    //TODO: Order의 OrderStatus enum 확인 구매확정이면 결제 취소못하게 하기 위함.
//    private void validateOrderStatus(Payment payment) {
//        if (payment.getOrder().getOrderStatus() == OrderStatus.ORDER_COMPLETED) {
//            throw new DomainException(ExceptionType.ORDER_ALREADY_COMPLETED);
//        }
//    }
}
