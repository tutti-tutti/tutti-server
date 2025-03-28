package com.tutti.server.core.returns.application;

import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.refund.application.RefundService;
import com.tutti.server.core.returns.domain.ReturnStatus;
import com.tutti.server.core.returns.domain.Returns;
import com.tutti.server.core.returns.infrastructure.ReturnsRepository;
import com.tutti.server.core.returns.payload.ReturnsRequest;
import com.tutti.server.core.returns.payload.ReturnsResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReturnsServiceImpl implements ReturnsService {

    private final OrderRepository orderRepository;
    private final ReturnsRepository returnsRepository;
    private final PaymentRepository paymentRepository;
    private final RefundService refundService;


    @Override
    @Transactional
    public void processReturnsRequest(ReturnsRequest request, Long authMemberId) {

        // 인가 적용
        orderRepository.findByIdAndMemberIdAndDeleteStatusFalse(request.orderId(), authMemberId);

        // 주문 번호가 있는지 확인한다.
        Order order = orderRepository.findOne(request.orderId());

        // 이미 반품된 주문인지 확인
        validateReturnStatus(request.orderId());

        Payment payment = getValidPayment(order);

        // updatedAt의 시간이후 일주일이 지나면 반품신청이 불가능하다.
        validateOrderEligibility(order);
        validatePaymentStatus(payment);

        // 반품을 신청하고 반품 엔티티에 값을 저장한다.
        Returns returns = returnsRepository.save(request.toEntity(order));

        // 5. 관리자가 없으므로 반품 완리면 환불 프로세스 탈 수 있도록
        autoRefund(returns, order, authMemberId);
    }

    @Override
    @Transactional(readOnly = true)
    public ReturnsResponse getReturnsByOrderId(Long orderId, Long authMemberId) {

        Returns returns = returnsRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.RETURNS_NOT_FOUND));

        return ReturnsResponse.fromEntity(returns);
    }

    private void validateOrderEligibility(Order order) {
        LocalDateTime returnDeadline = order.getUpdatedAt().plusDays(7);
        if (LocalDateTime.now().isAfter(returnDeadline)) {
            throw new DomainException(ExceptionType.RETURNS_REQUEST_EXPIRED);
        }
    }

    private void validatePaymentStatus(Payment payment) {
        if (!payment.getPaymentStatus().equals(PaymentStatus.DONE.name())) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_NOT_ALLOWED);
        }
    }

    private void validateReturnStatus(Long orderId) {
        returnsRepository.findByOrderId(orderId)
                .ifPresent(r -> {
                    if (r.getReturnStatus() == ReturnStatus.RETURN_COMPLETED) {
                        throw new DomainException(ExceptionType.RETURNS_ALREADY_COMPLETED);
                    }
                });
    }

    private void autoRefund(Returns returns, Order order, Long authMemberId) {
        if (returns.getReturnStatus() == ReturnStatus.RETURN_COMPLETED) {
            PaymentCancelRequest cancelRequest = PaymentCancelRequest.builder()
                    .orderNumber(order.getOrderNumber())
                    .cancelReason("반품 완료 자동 환불")
                    .build();

            refundService.requestRefund(cancelRequest, authMemberId);
        }
    }

    private Payment getValidPayment(Order order) {
        Payment payment = paymentRepository.findPaymentByOrderId(order.getId());

        if (!payment.getPaymentStatus().equals(PaymentStatus.DONE.name())) {
            throw new DomainException(ExceptionType.REFUND_REQUEST_NOT_ALLOWED);
        }

        LocalDateTime deadline = order.getUpdatedAt().plusDays(7);
        if (LocalDateTime.now().isAfter(deadline)) {
            throw new DomainException(ExceptionType.RETURNS_REQUEST_EXPIRED);
        }

        return payment;
    }
}
