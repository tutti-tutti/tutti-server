package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "faq")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faq extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private FaqCategory faqCategory; // 카테고리

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(nullable = false)
    private Integer authorId;

    @Column(nullable = false)
    private Boolean isView;

    @Column(nullable = false)
    private Long viewCnt = 0L;

    @Column(nullable = false)
    private Long positive = 0L;

    @Column(nullable = false)
    private Long negative = 0L;

    @Column
    private LocalDateTime deletedAt;
}