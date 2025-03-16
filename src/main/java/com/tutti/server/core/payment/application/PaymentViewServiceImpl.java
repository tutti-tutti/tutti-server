package com.tutti.server.core.payment.application;


import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentViewResponse;
import com.tutti.server.core.payment.payload.ViewMemberIdRequest;
import com.tutti.server.core.payment.payload.ViewOrderIdRequest;
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

    //meberId로 결제 조회
    @Override
    @Transactional(readOnly = true)
    public List<PaymentViewResponse> viewPaymentsByMemberId(ViewMemberIdRequest request) {

        // memberId로 결제 조회
        List<Payment> payments = findPaymentsByMemberId(request.memberId());

        // 결제 내역이 없으면 예외 던지기
        checkIfPaymentsExist(payments);

        // 결제 내역을 PaymentViewResponse로 변환
        return PaymentsToViewResponse(payments);
    }

    //orderId로 결제 조회
    @Override
    @Transactional(readOnly = true)
    public PaymentViewResponse viewPaymentByOrderId(ViewOrderIdRequest request) {

        // 결제 정보 조회
        Payment payment = paymentRepository.findByOrderId(request.orderId())
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));

        return PaymentViewResponse.fromEntity(payment);
    }

    private List<Payment> findPaymentsByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    private void checkIfPaymentsExist(List<Payment> payments) {
        if (payments.isEmpty()) {
            throw new DomainException(ExceptionType.PAYMENT_NOT_FOUND);
        }
    }

    private List<PaymentViewResponse> PaymentsToViewResponse(List<Payment> payments) {
        return payments.stream()
                .map(PaymentViewResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
