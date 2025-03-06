package com.tutti.server.core.stock.domain;

import com.tutti.server.core.product.domain.ProductItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skus")
@Getter
@NoArgsConstructor
public class Sku {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sku_id", nullable = false)
  private String skuCode;

  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_item_id", nullable = false)
  private ProductItem productItem;
}
