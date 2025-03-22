package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
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
    public List<PaymentViewResponse> viewPaymentByMemberId(Long memberId, Long authMemberId) {

        if (!memberId.equals(authMemberId)) {
            throw new DomainException(ExceptionType.UNAUTHORIZED_ERROR);
        }

        List<Payment> payments = paymentRepository.findByMemberId(memberId);

        if (payments.isEmpty()) {
            throw new DomainException(ExceptionType.PAYMENT_NOT_FOUND);
        }

        return convertToViewResponse(payments);
    }

    // orderId로 결제 조회
    @Override
    @Transactional(readOnly = true)
    public PaymentViewResponse viewPaymentByOrderId(Long orderId, Long authMemberId) {

        Order order = orderRepository.findByMemberIdAndIdAndDeleteStatusFalse(authMemberId, orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));

        Payment payment = findPaymentByOrderId(order.getId());
        return PaymentViewResponse.fromEntity(payment);
    }

    private List<PaymentViewResponse> convertToViewResponse(List<Payment> payments) {
        return payments.stream().map(PaymentViewResponse::fromEntity).toList();
    }

    private Payment findPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));
    }

}
