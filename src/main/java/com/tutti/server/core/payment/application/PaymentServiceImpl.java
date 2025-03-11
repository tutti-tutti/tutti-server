package com.tutti.server.core.payment.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import com.tutti.server.global.exception.OrderNotFoundException;
import com.tutti.server.global.exception.PaymentAlreadyCompletedException;
import com.tutti.server.global.exception.PaymentAmountMismatch;
import jakarta.transaction.Transactional;
import java.util.Optional;
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

        // 주문 정보 확인(이미 있으면 예외처리)
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(OrderNotFoundException::new);

        // 주문한 회원 가지고 오기
        Member member = order.getMember();

        // 기존 결제 여부 확인하는
        Optional<Payment> existingPayment = paymentRepository.findByOrder(order);
        if (existingPayment.isPresent()
                && existingPayment.get().getPaymentStatus() == PaymentStatus.PAYMENT_COMPLETED) {
            throw new PaymentAlreadyCompletedException();
        }

        // 주문 금액과 결제 요청 금액이 동일한지
        if (order.getTotalAmount() != request.amount()) {
            throw new PaymentAmountMismatch();
        }

        Payment payment = Payment.builder()
                .order(order)
                .member(member)
                .amount(request.amount())
                .orderName(request.orderName())
                .paymentStatus(PaymentStatus.PAYMENT_REQUESTED)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return new PaymentResponse(savedPayment.getId(),
                savedPayment.getPaymentStatus(),
                savedPayment.getOrderName(),
                savedPayment.getAmount(),
                savedPayment.getOrder().getId(),
                savedPayment.getCreatedAt());
    }
}
