package com.tutti.server.core.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category_maps")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ProductCategoryMap.ProductCategoryMapId.class)
public class ProductCategoryMap {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private ProductCategory category;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "delete_status", nullable = false)
  private boolean deleteStatus;

  @Builder
  public ProductCategoryMap(ProductCategory category, Product product, boolean deleteStatus) {
    this.category = category;
    this.product = product;
    this.deleteStatus = deleteStatus;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class ProductCategoryMapId implements Serializable {

    private Long category;
    private Long product;
  }
}
