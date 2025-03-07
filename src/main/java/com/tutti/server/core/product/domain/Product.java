package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "products")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Column(name = "bot_id", nullable = false)
  @Comment("botId")
  private String botId;

  // 전체 merge 후 uncomment
//  @Column(name = "store_id", nullable = false)
//  @ManyToOne
//  @JoinColumn(name = "store_id", nullable = false)
//  @Comment("storeId")
//  private Store storeId;

  @Column(name = "name", nullable = false)
  @Comment("productName")
  private String name;

  @Column(name = "title_url", nullable = false, length = 2083)
  @Comment("productImgUrl")
  private String titleUrl;

  @Column(name = "product_code")
  private String productCode;

  @Column(name = "detail_url", nullable = false, length = 2083)
  private String detailUrl;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "on_sales", nullable = false)
  private boolean onSales;

  @Column(name = "adult_only", nullable = false)
  private boolean adultOnly;

  @Column(name = "like_count", nullable = false)
  private int likeCount;

  // 필수로 설정하는 빌더
  @Builder
  public Product(String botId, String name, String titleUrl, String detailUrl,
      String categoryId, boolean onSales) {
    this.botId = botId;
    this.name = name;
    this.titleUrl = titleUrl;
    this.detailUrl = detailUrl;
    this.onSales = onSales;
    this.adultOnly = false;
    this.likeCount = 0;
  }
}
