package com.tutti.server.core.product.domain;

import com.tutti.server.core.store.domain.Store;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "bot_id")
    @NotNull
    @Comment("botId")
    private String botId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull
    @Comment("storeId")
    private Store storeId;

    @Column(name = "name")
    @NotNull
    @Comment("productName")
    private String name;

    @Column(name = "title_url", length = 2083, columnDefinition = "TEXT")
    @Lob
    @NotNull
    @Comment("productImgUrl")
    private String titleUrl;

    @Column(name = "product_code")
    @NotNull
    @Comment("productCode")
    private String productCode;

    @Column(name = "detail_url", length = 2083, columnDefinition = "TEXT")
    @Lob
    @Comment("이후추가")
    private String detailUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "on_sales")
    @NotNull
    private boolean onSales;

    @Column(name = "adult_only")
    @NotNull
    private boolean adultOnly;

    @Column(name = "max_quantity")
    @NotNull
    private int maxQuantity;

    @Column(name = "like_count")
    @NotNull
    private int likeCount;

    // 필수로 설정하는 빌더
    @Builder
    public Product(int original_price, String botId, Store storeId, String name, String titleUrl,
            String productCode,
            String detailUrl, String description, boolean onSales, boolean adultOnly,
            int maxQuantity,
            int likeCount) {
        this.originalPrice = original_price;
        this.botId = botId;
        this.storeId = storeId;
        this.name = name;
        this.titleUrl = titleUrl;
        this.productCode = productCode;
        this.detailUrl = detailUrl;
        this.description = description;
        this.onSales = onSales;
        this.adultOnly = adultOnly;
        this.maxQuantity = maxQuantity;
        this.likeCount = likeCount;
    }
}
