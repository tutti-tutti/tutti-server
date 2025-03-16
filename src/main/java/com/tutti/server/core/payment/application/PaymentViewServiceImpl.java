package com.tutti.server.core.payment.application;


import com.tutti.server.core.order.infrastructure.OrderItemRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentViewServiceImpl implements PaymentViewService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public List<PaymentViewResponse> viewPaymentsByMemberId(Long memberId) {

        List<Payment> payments = findPaymentsByMemberId(memberId);

        // 결제 내역이 없으면 예외 던지기
        checkIfPaymentsExist(payments);

        // 결제 내역을 PaymentViewResponse로 변환
        return mapPaymentsToViewResponse(payments);

    }

    private List<Payment> findPaymentsByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    private void checkIfPaymentsExist(List<Payment> payments) {
        if (payments.isEmpty()) {
            throw new DomainException(ExceptionType.PAYMENT_NOT_FOUND);
        }
    }

    private List<PaymentViewResponse> mapPaymentsToViewResponse(List<Payment> payments) {
        return payments.stream()
                .map(PaymentViewResponse::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public PaymentViewResponse getPaymentByOrderId(Long orderId) {

        // 유효성 검사
        if (orderId == null) {
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
