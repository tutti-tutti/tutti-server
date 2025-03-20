package com.tutti.server.core.member.domain;

import lombok.Getter;

@Getter
public enum TermsType {

    AGE_CONFIRMATION("만 14세 이상입니다.", true),
    TERMS_OF_SERVICE("이용약관 동의", true),
    ELECTRONIC_FINANCE("전자금융거래 이용약관 동의", true),
    PERSONAL_INFO_COLLECTION("개인정보 수집 및 이용 동의", true),
    PERSONAL_INFO_PROVISION("개인정보 제3자 제공 동의", true),
    MARKETING_EMAIL("마케팅 수신 이메일 동의", false),
    MARKETING_SMS("마케팅 수신 SMS, SNS 동의", false),
    APP_PUSH("앱 푸시 수신 동의", false);

    private final String displayName; // 약관의 한글 이름
    private final boolean required;

    TermsType(String displayName, boolean required) {
        this.displayName = displayName;
        this.required = required;
    }
}
