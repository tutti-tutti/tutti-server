package com.tutti.server.core.member.api;

import static com.tutti.server.core.member.utils.TokenExtractor.extractToken;

import com.tutti.server.core.member.application.MypageServiceSpec;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.member.payload.MemberResponse;
import com.tutti.server.core.member.payload.UpdateMemberRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MypageApi implements MypageApiSpec {

    private final MypageServiceSpec MypageService;
    private final JWTUtil jwtUtil;

    @GetMapping("/mypage")
    public ResponseEntity<MemberResponse> getMyPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader("Authorization") String authHeader) {

        String token = extractToken(authHeader);
        jwtUtil.validateAccessToken(token);

        MemberResponse response = MypageService.getMyPageInfo(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/mypage")
    public ResponseEntity<Map<String, String>> updateMyPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateMemberRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = extractToken(authHeader);
        jwtUtil.validateAccessToken(token);

        MypageService.updateMyPageInfo(userDetails.getUsername(), request);
        return ResponseEntity.ok(Map.of("message", "마이페이지 정보가 성공적으로 수정되었습니다."));
    }
}
