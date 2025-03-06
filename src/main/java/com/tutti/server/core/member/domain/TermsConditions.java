package com.tutti.server.core.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "terms_conditions")
public class TermsConditions {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false, unique = true)
    private TermsType type;

    @Column(columnDefinition = "TEXT")
    private String content; // 약관 내용

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public TermsConditions(TermsType type, String content) {
        this.type = type;
        this.content = content;
    }
}