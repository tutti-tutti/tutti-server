package com.tutti.server.core.refund.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.application.PaymentCancelService;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.payload.request.PaymentCancelRequest;
import com.tutti.server.core.refund.domain.Refund;
import com.tutti.server.core.refund.infrastructure.RefundRepository;
import com.tutti.server.core.refund.payload.RefundViewResponse;
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
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void requestRefund(PaymentCancelRequest request, Long authMemberId) {
        // 1. 결제 취소 처리
        Payment payment = cancelPayment(request, authMemberId);
        // 2. 결제한 회원 정보 조회
        Member member = getPaymentMember(payment);
        // 3. 환불 엔티티 생성 및 저장
        saveRefund(payment, member);
    }

    @Override
    @Transactional(readOnly = true)
    public RefundViewResponse getRefundView(Long orderId, Long authMemberId) {
        // 1. 회원이 해당 주문을 소유하고 있는지 검증
        validateUserOwnsOrder(orderId, authMemberId);
        // 2. 주문에 연결된 환불 정보 조회
        Refund refund = getRefundByOrderId(orderId);
        // 3. 환불 정보를 레코드로 변환하여 반환
        return RefundViewResponse.fromEntity(refund);
    }

    private Payment cancelPayment(PaymentCancelRequest request, Long memberId) {
        return paymentCancelService.paymentCancel(request, memberId);
    }

    private Member getPaymentMember(Payment payment) {
        return memberRepository.findOne(payment.getMember().getId());
    }

    private void saveRefund(Payment payment, Member member) {
        Refund refund = Refund.createCompletedRefund(payment, member);
        refundRepository.save(refund);
    }

    private void validateUserOwnsOrder(Long orderId, Long memberId) {
        orderRepository.findByIdAndMemberIdAndDeleteStatusFalse(orderId, memberId)
                .orElseThrow(() -> new DomainException(ExceptionType.UNAUTHORIZED_ERROR));
    }

    private Refund getRefundByOrderId(Long orderId) {
        return refundRepository.findByPaymentOrderId(orderId)
                .orElseThrow(() -> new DomainException(ExceptionType.REFUND_NOT_FOUND));
    }
}
