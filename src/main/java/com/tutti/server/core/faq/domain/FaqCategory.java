package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faq_categories")
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategory extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String mainCategory;

    @Column(length = 30)
    private String subCategory;

    @Column(length = 100)
    private String description;

}