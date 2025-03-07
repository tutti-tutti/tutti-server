package com.tutti.server.core.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "stock_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StockRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stock_request_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AlarmStatus status = AlarmStatus.WAITING;

  @Column(name = "notified_flag", nullable = false)
  private boolean notifiedFlag = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "member_id", nullable = false)
//  private Member member;

//  @Builder
//  public StockRequest(Product product, Member member) {
//    this.product = product;
//    this.member = member;
//    this.status = AlarmStatus.WAITING;
//    this.notifiedFlag = false;
//  }

  public void notifyStock() {
    this.status = AlarmStatus.NOTIFIED;
    this.notifiedFlag = true;
  }

  public void cancelRequest() {
    this.status = AlarmStatus.CANCELLED;
  }
}
