package com.tutti.server.core.support.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "system_processes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemProcess extends BaseEntity {

    @Column(length = 50)
    private String systemName;

    @Column(length = 100)
    private String description;

    @Builder
    public SystemProcess(String systemName, String description) {
        this.systemName = systemName;
        this.description = description;
    }
}
