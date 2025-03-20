package com.tutti.server.core.returns.api;

import com.tutti.server.core.returns.application.ReturnsService;
import com.tutti.server.core.returns.payload.ReturnsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/returns")
public class ReturnsApi implements ReturnsApiSpec {

    private final ReturnsService returnsService;

    @PostMapping("/request")
    public ResponseEntity<String> requestReturns(@RequestBody ReturnsRequest request) {
        returnsService.processReturnsRequest(request);
        return ResponseEntity.ok("반품 신청이 완료되었습니다.");
    }
}
