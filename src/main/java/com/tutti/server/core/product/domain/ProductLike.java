package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "product_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ProductLike.ProductLikeId.class)
public class ProductLike extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 전체 엔티티 merge 후 uncomment
//    @Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProductLikeId implements Serializable {
        private Long product;
        private Long member;
    }

    // 전체 엔티티 merge 후 uncomment
//    @Builder
//    public ProductLike(Product product, Member member) {
//        this.product = product;
//        this.member = member;
//    }
}
