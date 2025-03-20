package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "terms_conditions")
public class TermsConditions extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "terms_type", nullable = false, unique = true)
    private TermsType termsType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public String getDisplayName() {
        return this.termsType.getDisplayName();
    }

    public boolean isRequired() {
        return this.termsType.isRequired();
    }
}
