package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true)
    private String email; //필수

    @Column(unique = true)
    private String socialId; // 소셜 로그인 ID (Google, Naver의 고유 ID)

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialProvider socialProvider;

    //당장은 사용하지 않으나 자동 로그인 지원할 시 있어야 하는 엔티티
    @Column
    private String socialAccessToken;

    @Column(length = 50)
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    //선택 필드
    @Column(length = 50)
    private String name;

    @Column(length = 20, unique = true)
    private String phone;

    @Column
    private int age; //int는 not null

    @Column
    private LocalDate birthDate;

    @Column(length = 1)
    private String gender; // 'M' 또는 'F'

    @Column
    private LocalDateTime latestLoginDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private MemberStatus memberStatus;

    @Column(nullable = false)
    private boolean isEmailVerified;

    @Column(nullable = false)
    private boolean isAdult;

    @Builder
    public Member(String email, String password, String socialId, SocialProvider socialProvider) {
        this.email = email;
        this.password = password;
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        this.nickname = generateNickname(email); // 자동 생성
        this.memberStatus = MemberStatus.ACTIVE;
        this.isEmailVerified = false;
    }

    private String generateNickname(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return "user" + System.currentTimeMillis(); // 예외 처리
    }

    public static Member createEmailMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }

    public static Member createSocialMember(String socialId, SocialProvider socialProvider, String email) {
        return Member.builder()
                .socialId(socialId)
                .socialProvider(socialProvider)
                .email(email) // 소셜에서 이메일 제공 체크
                .password(null) // 소셜 회원가입은 비밀번호 없음
                .build();
    }

    public void verifyEmail() {
        this.isEmailVerified = true;
    }
}
