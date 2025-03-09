package com.tutti.server.core.payment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutti.server.core.payment.application.PaymentService;
import com.tutti.server.core.payment.domain.PaymentStatus;
import com.tutti.server.core.payment.payload.PaymentRequest;
import com.tutti.server.core.payment.payload.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    // 컨트롤러에 Mock 주입
    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        // Spring 컨텍스트 없이도 컨트롤러 객체를 직접 등록
        mockMvc = MockMvcBuilders
                .standaloneSetup(paymentController)
                // 필요시 ControllerAdvice나 필터 등도 추가
                //.setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("requestPayment() - 정상 요청 시 200 OK와 PaymentResponse 반환")
    void requestPayment_Success() throws Exception {
        // given
        PaymentRequest request = new PaymentRequest(1L, 1000, "테스트주문");
        PaymentResponse mockResponse = new PaymentResponse(999L, PaymentStatus.PAYMENT_REQUESTED);

        when(paymentService.requestPayment(any(PaymentRequest.class)))
                .thenReturn(mockResponse);

        // when & then
        mockMvc.perform(post("/api/v1/payments/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(999L))
                .andExpect(jsonPath("$.paymentStatus").value("PAYMENT_REQUESTED"));
    }

    @Test
    @DisplayName("requestPayment() - PaymentService에서 발생한 예외가 400으로 응답되는지 확인")
    void requestPayment_Exception() throws Exception {
        // given
        PaymentRequest request = new PaymentRequest(1L, 1000, "오류주문");

        when(paymentService.requestPayment(any(PaymentRequest.class)))
                .thenThrow(new IllegalArgumentException("유효하지 않은 결제 요청입니다."));

        // when & then
        mockMvc.perform(post("/api/v1/payments/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
