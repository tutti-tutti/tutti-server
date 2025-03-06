package com.tutti.server.core.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "search_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SearchHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "search_history_id")
  private Long id;

  @Column(nullable = false)
  private String keyword;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // 전체 merge후 uncomment
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "member_id", nullable = false)
//  private Member member;

  // 전체 merge후 uncomment
//  @Builder
//  public SearchHistory(String keyword, Member member) {
//    this.keyword = keyword;
//    this.member = member;
//  }
}
