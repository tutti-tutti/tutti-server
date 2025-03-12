package com.tutti.server.core.payment.application;

import com.tutti.server.core.common.exception.OrderNotFoundException;
import com.tutti.server.core.common.exception.PaymentAlreadyCompletedException;
import com.tutti.server.core.common.exception.PaymentAmountMismatch;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public PaymentResponse requestPayment(PaymentRequest request) {

        // 주문 정보 검증
        Order order = validateOrder(request.orderId());

        // 기존 결제 여부 검증
        validateDuplicatePayment(order.getId());

        // 결제 금액 검증
        validatePaymentAmount(order, request.amount());

        // 결제 객체 생성 및 저장
        Payment savedPayment = createAndSavePayment(order, request);

        // 응답 반환
        return PaymentResponse.fromEntity(savedPayment);
    }

    // 주문 정보 검증 메서드
    private Order validateOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));
    }

    // 기존 결제 여부 검증 메서드
    private void validateDuplicatePayment(Long orderId) {

        boolean exists = paymentRepository.existsByOrderId(orderId);

        if (exists) {
            throw new DomainException(ExceptionType.PAYMENT_ALREADY_PROCESSING);
        }

        paymentRepository.findByOrderId(orderId)
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.PAYMENT_COMPLETED)
                .ifPresent(payment -> {
                    throw new DomainException(ExceptionType.PAYMENT_ALREADY_COMPLETED);
                });
    }

    // 결제 금액 검증 메서드
    private void validatePaymentAmount(Order order, int amount) {
        if (order.getTotalAmount() != amount) {
            throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
        }
    }

    // 결제 객체 생성 및 저장 메서드
    private Payment createAndSavePayment(Order order, PaymentRequest request) {
        Payment payment = PaymentRequest.createPayment(order, order.getMember(), request.amount(),
                request.orderName());
        return paymentRepository.save(payment);
    }
}
