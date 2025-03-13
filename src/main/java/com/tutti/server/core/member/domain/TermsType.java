package com.tutti.server.core.member.domain;

import lombok.Getter;

@Getter
public enum TermsType {

    TERMS_OF_SERVICE("이용약관", true),
    PRIVACY_POLICY("개인정보처리방침", true),
    MARKETING_CONTENT("마케팅정보수신동의", false);

    private final String description;
    private final boolean required;

    TermsType(String description, boolean required) {
        this.description = description;
        this.required = required;
    }
}
