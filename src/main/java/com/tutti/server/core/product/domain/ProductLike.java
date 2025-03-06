package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product_likes")
@Getter
@EntityListeners(AuditingEntityListener.class)
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
