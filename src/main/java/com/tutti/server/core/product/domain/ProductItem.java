package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product_items")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductItem extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "original_price")
  private Integer originalPrice;

  @Column(name = "selling_price", nullable = false)
  private Integer sellingPrice;

  @Column(name = "options", columnDefinition = "TEXT")
  @Comment("색상=Red, 사이즈=S")
  private String options;

  // 필수 필드만 포함한 빌더 패턴
  @Builder
  public ProductItem(Product product, Integer originalPrice, Integer sellingPrice, String options) {
    this.product = product;
    this.originalPrice = originalPrice;
    this.sellingPrice = sellingPrice;
    this.options = options;

  }
}
