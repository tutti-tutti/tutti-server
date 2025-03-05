package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faq")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private FaqCategory faqCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    //이 엔티티는 관리자만 수정할 수 있으므로 필요가 없다 판단. 이 컬럼을 삭제하였습니다.
//    @Column(nullable = false)
//    private Integer authorId;

    @Column(nullable = false)
    private Boolean isView;

    // 래퍼 사용 : NPE 방지
    @Column(nullable = false)
    private Long viewCnt = 0L;

    @Column(nullable = false)
    private Long positive = 0L;

    @Column(nullable = false)
    private Long negative = 0L;

    @Column
    private LocalDateTime deletedAt;

    //To-Do: DTO 작성시 Builder 작성 예정입니다.
}