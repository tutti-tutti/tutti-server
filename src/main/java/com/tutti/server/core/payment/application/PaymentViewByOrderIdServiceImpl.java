package com.tutti.server.core.payment.application;

import com.tutti.server.core.order.infrastructure.OrderItemRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentViewByOrderIdServiceImpl implements PaymentViewByOrderIdService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    @Transactional
    public PaymentViewResponse getPaymentByOrderId(Long orderId) {

        // 유효성 검사
        if (orderId == null || orderId <= 0) {
            throw new NullPointerException("주문ID가 유효하지 않습니다.");
        }

        if (!orderRepository.existsById(orderId)) {
            throw new DomainException(ExceptionType.ORDER_NOT_FOUND);
        }

        // 결제 정보 조회
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));

        return PaymentViewResponse.fromEntity(payment);

    }
}
