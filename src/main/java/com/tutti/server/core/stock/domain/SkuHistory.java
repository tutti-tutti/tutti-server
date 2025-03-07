package com.tutti.server.core.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "sku_histories")
@Getter
@NoArgsConstructor
public class SkuHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sku_history_id")
  private Long skuHistoryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "skuHistoryType", nullable = false)
  private SkuHistoryType skuHistoryType;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sku_id", nullable = false)
  private Sku sku;
}
