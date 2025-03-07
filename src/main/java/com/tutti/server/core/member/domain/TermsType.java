package com.tutti.server.core.member.domain;

import lombok.Getter;

@Getter
public enum TermsType {

    TERMS_OF_SERVICE("이용약관"),
    PRIVACY_POLICY("개인정보처리방침"),
    MARKETING_CONTENT("마케팅정보수신동의");

    private final String description;

    TermsType(String description) {
        this.description = description;
    }
}