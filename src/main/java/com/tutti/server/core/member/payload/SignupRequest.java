//package com.tutti.server.core.member.payload;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//
//@Getter
//public class SignupRequest {
//
//    @Email
//    @NotBlank
//    private String email;
//
//    @NotBlank
//    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
//    private String password; // 대소문자 + 숫자 + 기호 조합으로 추후 변경
//    //    @Pattern(
////            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
////            message = "비밀번호는 최소 8자 이상이며, 대소문자 + 숫자 + 특수문자를 포함해야 합니다."
////    )
//    @NotBlank
//    private String passwordConfirm;
//}

package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(

        /** 사용자 이메일 (필수 입력) */
        @Email(message = "올바른 이메일 형식을 입력해야 합니다.")
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        String email,

        /** 사용자 비밀번호 (필수 입력, 최소 8자, 대소문자+숫자+특수문자 포함) */
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "비밀번호는 최소 8자 이상이며, 대소문자 + 숫자 + 특수문자를 포함해야 합니다."
        )
        String password,

        /** 비밀번호 확인 (필수 입력, 실제 검증 로직에서 password와 비교해야 함) */
        @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
        String passwordConfirm
) {

}
