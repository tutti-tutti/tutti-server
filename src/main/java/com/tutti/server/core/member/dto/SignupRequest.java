package com.tutti.server.core.member.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password; // 대소문자 + 숫자 + 기호 조합으로 추후 변경
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//            message = "비밀번호는 최소 8자 이상이며, 대소문자 + 숫자 + 특수문자를 포함해야 합니다."
//    )
    @NotBlank
    private String passwordConfirm;
}
