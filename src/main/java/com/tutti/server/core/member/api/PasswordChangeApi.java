package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.PasswordChangeServiceImpl;
import com.tutti.server.core.member.payload.PasswordChangeRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class PasswordChangeApi implements PasswordChangeApiSpec {

    private final PasswordChangeServiceImpl passwordChangeServiceImpl;

    @Override
    @PostMapping("/password/change")
    public ResponseEntity<Map<String, String>> changePassword(
        @RequestBody @Valid PasswordChangeRequest request,
        @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        passwordChangeServiceImpl.changePassword(email, request);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }
}
