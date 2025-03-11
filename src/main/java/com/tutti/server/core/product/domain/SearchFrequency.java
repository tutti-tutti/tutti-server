package com.tutti.server.core.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "search_frequencies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SearchFrequency {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  @Column(name = "search_frequency_id")
  private Long id;

  @Column(name = "query")
  private String query;

  @Column(name = "frequency")
  private Long frequency;

  @Builder(toBuilder = true)
  public SearchFrequency(String query, Long frequency) {
    this.query = query;
    this.frequency = frequency;
  }

  public void incrementFrequency() {
    this.frequency++;
  }
}
