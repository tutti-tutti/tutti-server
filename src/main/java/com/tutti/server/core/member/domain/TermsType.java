package com.tutti.server.core.member.domain;
import lombok.Getter;

@Getter
public enum TermsType{

    TERMS_OF_SERVICE(1,"이용약관"),
    PRIVACY_POLICY(2,"개인정보처리방침"),
    MARKETING_CONTENT(3, "마케팅정보수신동의");

    private final int code;
    private final String description;
    private TermsType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TermsType fromCode(int code) {
        for (TermsType type : TermsType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TermsType code: " + code);
    }

}


