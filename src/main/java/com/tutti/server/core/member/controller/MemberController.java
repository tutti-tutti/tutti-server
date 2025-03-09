package com.tutti.server.core.member.controller;

import com.tutti.server.core.member.dto.SignupRequest;
import com.tutti.server.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입 API
    @PostMapping("/signup/email")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "회원가입이 완료되었습니다."));
    }
}

