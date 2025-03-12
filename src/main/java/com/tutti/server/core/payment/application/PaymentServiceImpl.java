package com.tutti.server.core.payment.application;

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

        // 주문 정보 확인(이미 있으면 예외처리)
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new DomainException(ExceptionType.ORDER_NOT_FOUND));

        // 주문한 회원 가지고 오기
        Member member = order.getMember();

        // 기존 결제 여부 확인하는
        paymentRepository.findByOrderId(order.getId())
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.PAYMENT_COMPLETED)
                .ifPresent(payment -> {
                    throw new DomainException(ExceptionType.PAYMENT_ALREADY_COMPLETED);
                });

        // 주문 금액과 결제 요청 금액이 동일한지
        if (order.getTotalAmount() != request.amount()) {
            throw new DomainException(ExceptionType.PAYMENT_AMOUNT_MISMATCH);
        }

        //Builder를 Entity에서 처리
        Payment payment = PaymentResponse.createPayment(order, member, request.amount(),
                request.orderName());
        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.fromEntity(savedPayment);
    }
}
