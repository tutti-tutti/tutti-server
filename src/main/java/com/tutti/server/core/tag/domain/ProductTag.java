package com.tutti.server.core.tag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTag extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ProductTag(Product product, Tag tag) {
        this.product = product;
        this.tag = tag;
    }
}
