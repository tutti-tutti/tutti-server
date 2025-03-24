package com.tutti.server.core.member.api;

import com.tutti.server.core.member.application.MypageServiceSpec;
import com.tutti.server.core.member.payload.MemberResponse;
import com.tutti.server.core.member.payload.UpdateMemberRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@SecurityRequirement(name = "Bearer Authentication")
public class MypageApi implements MypageApiSpec {

    private final MypageServiceSpec mypageService;

    @GetMapping("/mypage")
    public ResponseEntity<MemberResponse> getMyPage(
            @AuthenticationPrincipal UserDetails userDetails) {
        MemberResponse response = mypageService.getMyPageInfo(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/mypage")
    public ResponseEntity<?> updateMyPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateMemberRequest request) {

        mypageService.updateMyPageInfo(userDetails.getUsername(), request);
        return ResponseEntity.ok(Map.of("message", "마이페이지 정보가 성공적으로 수정되었습니다."));
    }
}
