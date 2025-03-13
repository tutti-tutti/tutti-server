package com.tutti.server.core.member.payload;

import jakarta.validation.constraints.NotNull;

public record TermsAgreementRequest(
        @NotNull(message = "약관 ID는 필수 입력 항목입니다.")
        Long termId,

        @NotNull(message = "약관 동의 여부는 필수 입력 항목입니다.")
        Boolean agreed
) {

}
