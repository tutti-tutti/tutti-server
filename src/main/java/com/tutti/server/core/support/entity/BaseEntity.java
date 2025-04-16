package com.tutti.server.core.support.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @Getter
    @Comment("PK, 고유 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delete_status", nullable = false)
    @Comment("ROW 삭제 여부")
    private boolean deleteStatus;

    @Column
    @Comment("생성일")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @Comment("수정일")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void delete() {
        this.deleteStatus = true;
    }
}
