package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faqs")
@Getter
@NoArgsConstructor
public class Faq extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private FaqCategory faqCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(nullable = false)
    private Boolean isView;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCnt = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long positive = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long negative = 0L;

    @Column
    private LocalDateTime deletedAt;

    @Builder
    public Faq(FaqCategory faqCategory, String question, String answer, Boolean isView) {
        this.faqCategory = faqCategory;
        this.question = question;
        this.answer = answer;
        this.isView = isView;
    }

    // FAQ 수정 메서드 (null 값 무시)
    public void updateFaq(String question, String answer, Boolean isView) {
        if (question != null) {
            this.question = question;
        }
        if (answer != null) {
            this.answer = answer;
        }
        if (isView != null) {
            this.isView = isView;
        }
    }

    // 조회수 증가
    public void incrementViewCount() {
        this.viewCnt++;
    }

    // 긍정 평가 증가
    public void incrementPositive() {
        this.positive++;
    }

    // 부정 평가 증가
    public void incrementNegative() {
        this.negative++;
    }

    // 삭제
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
