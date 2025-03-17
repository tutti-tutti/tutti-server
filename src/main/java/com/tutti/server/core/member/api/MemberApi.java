package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.MemberServiceImpl;
import com.tutti.server.core.member.payload.SignupRequest;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi implements MemberApiSpec {

    private final MemberServiceImpl memberServiceImpl;

    // 회원가입 API
    @Override
    public ResponseEntity<Map<String, String>> signup(SignupRequest request) {
        memberServiceImpl.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "회원가입이 완료되었습니다."));
    }
}
