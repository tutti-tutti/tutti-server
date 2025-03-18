package com.tutti.server.core.refund.api;

import com.tutti.server.core.refund.application.RefundService;
import com.tutti.server.core.refund.payload.RefundRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refund")
public class RefundApi implements RefundApiSpec {

    private final RefundService refundService;

    @PostMapping("/request")
    public ResponseEntity<Void> requestRefund(@Valid @RequestBody RefundRequest request) {
        refundService.requestRefund(request);
        return ResponseEntity.ok().build();
    }
}
