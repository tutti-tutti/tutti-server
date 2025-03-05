package com.tutti.server.core.product.domain;

import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "bot_id", nullable = false, length = 255)
    @Comment("botId")
    private String botId;

    @Column(name = "store_id", nullable = false)
    @Comment("storeId FK")
    private Long storeId;

    @Column(name = "name", nullable = false, length = 255)
    @Comment("productName")
    private String name;

    @Column(name = "title_url", nullable = false, length = 2083)
    @Comment("productImgUrl")
    private String titleUrl;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "detail_url", nullable = false, length = 2083)
    private String detailUrl;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "on_sales", nullable = false)
    private boolean onSales;

    @Column(name = "adult_only", nullable = false)
    private boolean adultOnly = false;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    // 상품명만 필수로 설정하는 빌더
    @Builder
    public Product(String botId, Long storeId, String name, String titleUrl, String detailUrl, String categoryId, boolean onSales) {
        this.botId = botId;
        this.storeId = storeId;
        this.name = name;
        this.titleUrl = titleUrl;
        this.detailUrl = detailUrl;
        this.onSales = onSales;
        this.adultOnly = false;
        this.likeCount = 0;
    }
}