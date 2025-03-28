package com.tutti.server.core.member.api;

import com.tutti.server.core.member.payload.MemberResponse;
import com.tutti.server.core.member.payload.UpdateMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "마이페이지 API", description = "마이페이지 조회 및 수정 API")
@SecurityRequirement(name = "Bearer Authentication")
public interface MypageApiSpec {

    @Operation(summary = "마이페이지 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    ResponseEntity<MemberResponse> getMyPage(UserDetails userDetails, String authHeader);

    @Operation(summary = "마이페이지 수정", description = "로그인한 사용자의 정보를 수정합니다.")
    ResponseEntity<Map<String, String>> updateMyPage(UserDetails userDetails,
            UpdateMemberRequest request, String authHeader);
}
