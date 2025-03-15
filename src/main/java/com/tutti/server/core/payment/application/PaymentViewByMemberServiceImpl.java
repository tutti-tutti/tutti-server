package com.tutti.server.core.payment.application;

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
public class PaymentViewByMemberServiceImpl implements PaymentViewByMemberService {

    private final PaymentRepository paymentRepository;

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
            throw new DomainException(ExceptionType.MEMBER_NOT_FOUND);
        }
    }

    private List<PaymentViewResponse> mapPaymentsToViewResponse(List<Payment> payments) {
        return payments.stream()
                .map(PaymentViewResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
