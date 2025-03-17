package com.tutti.server.core.payment.application;

import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.TossPaymentsCancelResponse;
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

    @Override
    @Transactional
    public void paymentCancel(PaymentCancelRequest request) {
        // 결제 정보 조회 및 검증
        Payment payment = findPaymentByOrderId(request.orderId());

        checkPaymentCancelEligibility(payment);

        // TossPayments API 호출 및 응답 처리
        TossPaymentsCancelResponse parsedResponse = tossPaymentService.cancelPayment(
                payment, request.getCancelReason());

        // 결제 상태 업데이트 및 결제 이력 저장
        updatePaymentStatus(payment, parsedResponse);
        paymentHistoryService.savePaymentHistory(payment);
    }

    private Payment findPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
    }

    // 결제 상태 취소로 업데이트
    private void updatePaymentStatus(Payment payment, TossPaymentsCancelResponse response) {
        payment.cancelPayment(response.canceledAt());
        paymentRepository.save(payment);
    }

    // 결제 상태 확인
    private void checkPaymentCancelEligibility(Payment payment) {
        if (payment.getPaymentStatus() != PaymentStatus.DONE) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_NOT_ALLOWED);
        }
        validateRefundPeriod(payment);
//        validateOrderStatus(payment);

    }

    // 결제 취소 기간 검증(7일이 후면 자동 구매확정이므로)
    private void validateRefundPeriod(Payment payment) {
        LocalDateTime refundDeadline = payment.getCompletedAt().plusDays(7);
        if (LocalDateTime.now().isAfter(refundDeadline)) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_EXPIRED);
        }
    }

    //TODO: Order의 OrderStatus enum 확인 구매확정이면 결제 취소못하게 하기 위함.
//    private void validateOrderStatus(Payment payment) {
//        if (payment.getOrder().getOrderStatus() == OrderStatus.ORDER_COMPLETED) {
//            throw new DomainException(ExceptionType.ORDER_ALREADY_COMPLETED);
//        }
//    }
}
