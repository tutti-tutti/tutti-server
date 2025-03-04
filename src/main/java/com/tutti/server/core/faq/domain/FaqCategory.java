package com.tutti.server.core.faq.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faq_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqCategory extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String mainCategory;

    @Column(length = 30)
    private String subCategory;

    @Column(length = 100)
    private String description;
}
