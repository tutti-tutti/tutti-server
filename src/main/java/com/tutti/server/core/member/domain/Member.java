package com.tutti.server.core.member.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(length = 255, unique = true)
    private String socialId;

    @Column(length = 50)
    private String socialProvider;

    @Column(length = 255)
    private String socialAccessToken;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(length = 20, unique = true)
    private String phone;

    @Column
    private Integer age; // 만 나이

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
    private boolean isVerified = false;

    @Column(nullable = false)
    private boolean isAdult = false;

    @Builder
    public Member(String email, String socialId, String socialProvider, String socialAccessToken,
                  String password, String name, String nickname, String phone, Integer age,
                  LocalDate birthDate, String gender, LocalDateTime latestLoginDate,
                  MemberStatus memberStatus, LocalDateTime createdAt, boolean isVerified, boolean isAdult) {
        this.email = email;
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        this.socialAccessToken = socialAccessToken;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.latestLoginDate = latestLoginDate;
        this.memberStatus = memberStatus;
        this.isVerified = isVerified;
        this.isAdult = isAdult;
    }
}
