package com.tutti.server.core.product.payload;

/*
 * JIHYE API에서 받아오는 JSON 구조에 맞춰 작성한 DTO
 * */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class ProductApiResponse {

  private Long id;

  @JsonProperty("botId")
  private String botId;

  @JsonProperty("storeId")
  private String storeId;

  @JsonProperty("productName")
  private String productName;

  @JsonProperty("originalPrice")
  private Integer originalPrice;

  @JsonProperty("dcPrice")
  private Integer dcPrice;

  @JsonProperty("productImgUrl")
  private String productImgUrl;

  @JsonProperty("productCode")
  private String productCode;

  @JsonProperty("detailUrl")
  private String detailUrl;

  @JsonProperty("description")
  private String description;

  @JsonProperty("qnaOptionBtn")
  private Boolean qnaOptionBtn;

  @JsonProperty("delete")
  private Boolean delete;

  @JsonProperty("category")
  private String category;

  @JsonProperty("similarOptionBtn")
  private Boolean similarOptionBtn;

  @JsonProperty("onSales")
  private Boolean onSales;

  @JsonProperty("labels")
  private String labels;

  @JsonProperty("sns")
  private List<String> sns; // sns 링크들 (빈 배열 가능)

  @JsonProperty("published_at")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime publishedAt;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_by")
  private String updatedBy;

  @JsonProperty("created_at")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime updatedAt;

  @JsonProperty("secret")
  private String secret;

  @JsonProperty("ecField")
  private String ecField;

  @JsonProperty("guardYn")
  private String guardYn;

  // builder 추가
}
