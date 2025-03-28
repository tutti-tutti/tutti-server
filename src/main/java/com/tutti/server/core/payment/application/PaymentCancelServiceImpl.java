package com.tutti.server.core.payment.application;

import com.tutti.server.core.order.application.OrderService;
import com.tutti.server.core.order.domain.CreatedByType;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderHistoryRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.payment.payload.response.TossPaymentsCancelResponse;
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
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderHistoryRepository orderHistoryRepository;

    @Override
    @Transactional
    public Payment paymentCancel(PaymentCancelRequest request, Long authMemberId) {

        Order order = orderRepository.findByOrderNumberAndMemberId(request.orderNumber(),
                        authMemberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));

        // 결제 정보 조회 및 검증
        Payment payment = paymentRepository.findPaymentByOrderId(order.getId());
        checkPaymentCancelEligibility(payment);

        // TossPayments API 호출 및 응답 처리
        TossPaymentsCancelResponse cancelResponse = tossPaymentService.cancelPayment(payment,
                request.cancelReason());

        if (!"CANCELED".equals(cancelResponse.status())) {
            throw new DomainException(ExceptionType.TOSS_CANCEL_FAIL);
        }

        // 결제 상태 업데이트 및 결제 이력 저장
        updatePaymentStatus(payment);
        paymentHistoryService.savePaymentHistory(payment);

        // 주문 상태 업데이트 및 주문 이력 생성
        order.updateOrderStatus(PaymentStatus.CANCELED.name());
        orderService.createOrderHistory(order, CreatedByType.MEMBER, authMemberId);

        return payment;
    }

    // 결제 상태 취소로 업데이트
    private void updatePaymentStatus(Payment payment) {
        payment.cancelPayment();
        paymentRepository.save(payment);
    }

    // 결제 취소 시 확인(결제 상태(DONE), 환불상태(RefundNotCompleted), 주문상태(구매확정이면 불가)
    private void checkPaymentCancelEligibility(Payment payment) {
        if (!payment.getPaymentStatus().equals(PaymentStatus.DONE.name())) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_NOT_ALLOWED);
        }
        validateRefundPeriod(payment);
        validateRefundNotCompleted(payment.getOrder().getId());
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
}
