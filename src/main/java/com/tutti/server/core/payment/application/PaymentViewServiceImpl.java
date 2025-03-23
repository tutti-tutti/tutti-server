package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.response.PaymentViewResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentViewServiceImpl implements PaymentViewService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    // memberId로 결제 조회
    @Override
    @Transactional(readOnly = true)
    public PaymentViewResponse viewPaymentByMemberId(Long paymentId, Long authMemberId) {

        Payment payment = paymentRepository.findByIdAndMemberId(paymentId, authMemberId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));

        return PaymentViewResponse.fromEntity(payment);
    }

    // orderId로 결제 조회
    @Override
    @Transactional(readOnly = true)
    public PaymentViewResponse viewPaymentByOrderId(Long orderId, Long authMemberId) {

        orderRepository.findByIdAndMemberIdAndDeleteStatusFalse(orderId, authMemberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));

        Payment payment = findPaymentByOrderId(orderId);
        return PaymentViewResponse.fromEntity(payment);
    }

    private Payment findPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
    }

}
