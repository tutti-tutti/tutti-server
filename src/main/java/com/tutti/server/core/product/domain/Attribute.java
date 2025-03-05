package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attribute")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attribute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Builder
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
