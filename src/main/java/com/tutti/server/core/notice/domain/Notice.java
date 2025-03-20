package com.tutti.server.core.notice.domain;

import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private boolean isPublished = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store storeId;

    @Builder
    public Notice(String title, String content, Member writer, Store storeId) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.storeId = storeId;
    }

    public void increaseViews() {
        this.views++;
    }

    public void updateNotice(String title, String content, boolean isPublished) {
        this.title = title;
        this.content = content;
        this.isPublished = isPublished;
    }
}
