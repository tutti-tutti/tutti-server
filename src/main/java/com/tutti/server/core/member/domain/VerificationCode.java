package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "verification_codes")
public class VerificationCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id", nullable = false)
    private Long id; // 인증번호 ID

    @Column(nullable = false, length = 50)
    private String email; // 이메일

    @Column(nullable = false, length = 6)
    private String verificationCode; // 인증번호 (6자리)

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 시간

    @Column(nullable = false)
    private boolean isVerified; // 인증 여부

    @Builder
    public VerificationCode(String email, String verificationCode, LocalDateTime expiresAt) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.expiresAt = expiresAt;
        this.isVerified = false;
    }

    // 인증 성공 시 호출할 메서드
    public void verify() {
        if (isExpired()) {
            throw new DomainException(ExceptionType.INVALID_VERIFICATION_CODE);
        }
        this.isVerified = true;
    }

    public void expire() {
        this.isVerified = false;
    }

    // 인증번호 만료 여부 체크
    public boolean isExpired() {
        return expiresAt == null || LocalDateTime.now().isAfter(expiresAt);
    }
}
