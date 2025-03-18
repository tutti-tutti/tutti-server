package com.tutti.server.core.refund.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.payment.application.PaymentCancelService;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentCancelRequest;
import com.tutti.server.core.refund.domain.Refund;
import com.tutti.server.core.refund.infrastructure.RefundRepository;
import com.tutti.server.core.refund.payload.RefundRequest;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentCancelService paymentCancelService;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void requestRefund(RefundRequest request) {
        // 결제 취소 처리 (기존 결제 취소 로직 재사용)
        paymentCancelService.paymentCancel( //TODO 환불 상태가 환불완료인지도 검증해야함.
                new PaymentCancelRequest(request.orderId(), request.reason()));

        // 결제 정보 조회
        Payment payment = paymentRepository.findByOrderId(request.orderId())
                .orElseThrow(() -> new DomainException(ExceptionType.PAYMENT_NOT_FOUND));

        // 결제한 회원 정보 조회
        Member member = memberRepository.findById(payment.getMember().getId())
                .orElseThrow(() -> new DomainException(ExceptionType.MEMBER_NOT_FOUND));

        // 환불 엔티티 생성 및 저장
        Refund refund = Refund.createCompletedRefund(payment, member);
        refundRepository.save(refund);
    }
}
