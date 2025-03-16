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

    @Column(name = "is_view", nullable = false)
    private boolean isView;

    @Column(name = "view_cnt", nullable = false)
    private long viewCnt = 0L;

    @Column(nullable = false)
    private long positive = 0L;

    @Column(nullable = false)
    private long negative = 0L;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Faq(FaqCategory faqCategory, String question, String answer, boolean isView) {
        this.faqCategory = faqCategory;
        this.question = question;
        this.answer = answer;
        this.isView = isView;
    }

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

    public void updateCategory(FaqCategory faqCategory) {
        if (faqCategory != null) {
            this.faqCategory = faqCategory;
        }
    }

    public void incrementViewCount() {
        this.viewCnt++;
    }

    public void incrementPositive() {
        this.positive++;
    }

    public void incrementNegative() {
        this.negative++;
    }

    public void markAsDeleted() {
        super.delete();
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isSameViewStatus(Boolean isView) {
        return Boolean.TRUE.equals(this.isView) == Boolean.TRUE.equals(isView);
    }

    public String getCategoryName() {
        if (faqCategory == null) {
            return "카테고리 없음";
        }
        return faqCategory.getMainCategory() + (faqCategory.getSubCategory() != null ? " > "
            + faqCategory.getSubCategory() : "");
    }
}
