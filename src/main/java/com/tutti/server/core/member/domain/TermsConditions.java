package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "terms_conditions")
public class TermsConditions extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name="terms_type",nullable = false, unique = true)
    private TermsType termsType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 약관 내용

    @Builder
    public TermsConditions(TermsType termsType, String content) {
        this.termsType = termsType;
        this.content = content;
    }
}
