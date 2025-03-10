package com.tutti.server.core.payment.application;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.domain.SocialProvider;
import com.tutti.server.core.order.domain.Order;
import com.tutti.server.core.order.infrastructure.OrderRepository;
import com.tutti.server.core.payment.domain.Payment;
import com.tutti.server.core.payment.domain.PaymentMethodType;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.infrastructure.PaymentRepository;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService; // 테스트 대상

    @Test
    @DisplayName("requestPayment() - 정상 결제 요청 시 PaymentResponse 반환")
    void requestPaymentSuccess() {
        // given
        Long orderId = 1L;
        int amount = 1000;
        String orderName = "테스트주문";

        // Member 엔티티 생성
        Member member = Member.builder()
                .email("testuser@example.com")
                .password("test1234")
                .socialId(null)
                .socialProvider(SocialProvider.GOOGLE) // 가정
                .build();

        // Order 엔티티 생성
        Order order = Order.builder()
                .member(member)
                .deliveryFee(0)
                .totalAmount(amount)
                .paymentType(PaymentMethodType.CARD)
                .orderStatus("CREATED")
                .completed_at(null)
                .orderCount(1)
                .build();

        // 결제 요청
        PaymentRequest request = new PaymentRequest(orderId, amount, orderName);

        // Mock 설정: 주문이 존재하며, 기존 결제는 없는 상태
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrder(order)).thenReturn(Optional.empty());

        // Payment save() 시 반환될 가짜 엔티티
        Payment savedPayment = Payment.builder()
                .order(order)
                .member(member)
                .amount(amount)
                .orderName(orderName)
                .paymentStatus(PaymentStatus.PAYMENT_REQUESTED)
                .build();
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        // when
        PaymentResponse response = paymentService.requestPayment(request);

        // response가 잘 왔는지 확인
        System.out.println("결제 응답: " + response);

        // then
        assertThat(response.paymentStatus()).isEqualTo(PaymentStatus.PAYMENT_REQUESTED);

        // 실제 save() 호출 시 넘어간 Payment 객체 검증
        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());
        Payment capturedPayment = captor.getValue();

        assertThat(capturedPayment.getOrder()).isEqualTo(order);
        assertThat(capturedPayment.getMember()).isEqualTo(member);
        assertThat(capturedPayment.getAmount()).isEqualTo(amount);
        assertThat(capturedPayment.getOrderName()).isEqualTo(orderName);
        assertThat(capturedPayment.getPaymentStatus()).isEqualTo(PaymentStatus.PAYMENT_REQUESTED);

        // findById, findByOrder, save 메서드 호출 횟수 확인
        verify(orderRepository, times(1)).findById(orderId);
        verify(paymentRepository, times(1)).findByOrder(order);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("requestPayment() - 주문 정보가 없을 때 예외 발생")
    void requestPaymentNoOrder() {
        // given
        Long wrongOrderId = 999L;
        PaymentRequest request = new PaymentRequest(wrongOrderId, 2000, "잘못된주문");

        // Mock 설정: DB에 해당 주문이 없음
        when(orderRepository.findById(wrongOrderId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> paymentService.requestPayment(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 주문을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("requestPayment() - 이미 결제가 완료된 주문일 때 예외 발생")
    void requestPaymentAlreadyCompleted() {
        // given
        Long orderId = 2L;
        int amount = 3000;

        // Member, Order 생성
        Member member = Member.builder()
                .email("alreadyDone@example.com")
                .password("pw")
                .build();

        Order order = Order.builder()
                .member(member)
                .deliveryFee(0)
                .totalAmount(amount)
                .paymentType(PaymentMethodType.CARD)
                .orderStatus("COMPLETED")
                .completed_at(LocalDateTime.now())
                .orderCount(1)
                .build();

        // 이미 결제가 완료된 Payment
        Payment completedPayment = Payment.builder()
                .order(order)
                .member(member)
                .amount(amount)
                .orderName("이미결제완료주문")
                .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrder(order)).thenReturn(Optional.of(completedPayment));

        PaymentRequest request = new PaymentRequest(orderId, amount, "이미완료주문");

        // when & then
        assertThatThrownBy(() -> paymentService.requestPayment(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 결제가 완료된 주문입니다.");
    }

    @Test
    @DisplayName("requestPayment() - 주문 금액과 결제 금액이 불일치할 때 예외 발생")
    void requestPaymentAmountMismatch() {
        // given
        Long orderId = 3L;

        // Order에 totalAmount=10000, 요청은 5000
        Order order = Order.builder()
                .member(Member.builder().email("mismatch@example.com").build())
                .totalAmount(10000)
                .paymentType(PaymentMethodType.CARD)
                .orderStatus("CREATED")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrder(order)).thenReturn(Optional.empty());

        // 주문 금액 = 10,000 vs 결제 요청 금액 = 5,000
        PaymentRequest request = new PaymentRequest(orderId, 5000, "금액불일치");

        // when & then
        assertThatThrownBy(() -> paymentService.requestPayment(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("주문 금액과 결제 금액이 일치하지 않습니다.");
    }
}
