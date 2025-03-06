package com.tutti.server.core.member.domain;

import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "viewed_products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewedProduct extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // FK - 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // FK - 상품

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt; // 조회 시간

    @Builder
    public ViewedProduct(Member member, Product product, LocalDateTime viewedAt) {
        this.member = member;
        this.product = product;
        this.viewedAt = viewedAt;
    }

    //상품 조회 기록 삭제
    public void markAsDeleted() {
        super.delete(); // BaseEntity의 deleteStatus를 true로 변경
    }

    //조회 기록 업데이트
    public void updateViewedAt(LocalDateTime newViewedAt) {
        this.viewedAt = newViewedAt;
    }
}
