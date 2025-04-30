package com.tutti.server.core.returns.api;


import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.returns.application.ReturnsService;
import com.tutti.server.core.returns.payload.ReturnsRequest;
import com.tutti.server.core.returns.payload.ReturnsResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/returns")
public class ReturnsApi implements ReturnsApiSpec {

    private final ReturnsService returnsService;

    @PostMapping("/request")
    public ResponseEntity<String> requestReturns(@RequestBody ReturnsRequest request,
            @AuthenticationPrincipal
            CustomUserDetails user) {
        returnsService.processReturnsRequest(request, user.getMemberId());
        return ResponseEntity.ok("반품 신청이 완료되었습니다.");
    }

    @GetMapping("/order/{orderId}")
    public ReturnsResponse getReturnsByOrderId(@PathVariable Long orderId, @AuthenticationPrincipal
    CustomUserDetails user) {
        return returnsService.getReturnsByOrderId(orderId, user.getMemberId());
    }
}
