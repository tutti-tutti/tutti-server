package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faq_categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategory extends BaseEntity {

    @Column(name = "main_category", nullable = false, length = 30)
    private String mainCategory;

    @Column(name = "sub_category", length = 30)
    private String subCategory;

    @Column(length = 100)
    private String description;

    public void FaqCategoryResponse(FaqCategory category) {
        this.mainCategory = category.getMainCategory();
        this.subCategory = category.getSubCategory();
    }
}
