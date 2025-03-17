package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.PasswordChangeServiceImpl;
import com.tutti.server.core.member.payload.PasswordChangeRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordChangeApi implements PasswordChangeApiSpec {

    private final PasswordChangeServiceImpl passwordChangeServiceImpl;

    @Override
    public ResponseEntity<Map<String, String>> changePassword(PasswordChangeRequest request,
            UserDetails userDetails) {

        String email = userDetails.getUsername();
        passwordChangeServiceImpl.changePassword(email, request);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }
}
