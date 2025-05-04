package com.tutti.server.core.tag.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

    @Column(name = "tag_name", nullable = false, unique = true)
    private String tagName;

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
