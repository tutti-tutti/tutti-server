# **[리뷰 기능 요구사항 정의]**

---

## **1. (CREATE) 사용자는 상품에 대한 리뷰를 작성할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `POST /reviews`

- [X] 사용자는 주문한 상품에 대한 리뷰를 작성할 수 있다.
- [X] 리뷰 작성 시, 평점(`rating`)과 리뷰 내용(`content`)을 포함해야 한다.
- [ ] 사용자는 리뷰에 이미지를 첨부할 수 있다.(선택)
- [X] 리뷰 작성 시, 유효한 `order_item_id`가 필요하다.
- [X] 리뷰를 작성하려면 인증된 사용자여야 한다.

**Request**:

```json
{
  "order_item_id": "주문한 상품의 ID (필수)",
  "rating": "상품 평점 (1~5) (필수)",
  "content": "리뷰 내용 (필수)",
  "review_images": [
    "리뷰 이미지 URL 목록 (선택)"
  ]
}
```

**Response**:

- 성공적으로 작성된 리뷰의 ID를 반환한다.

---

## **2. (READ) 사용자는 특정 상품의 리뷰 목록을 조회할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `GET /reviews/{product_id}`

- [ ] 사용자는 특정 상품에 대한 리뷰 목록을 조회할 수 있다.
- [ ] 로그인하지 않은 사용자도 조회할 수 있다.
- [ ] 요청 시, 정렬 기준과 요청할 리뷰 개수를 선택할 수 있다.
- [ ] 정렬 기준:
    - `created_at_desc`: 최신순 (기본값)
    - `rating_desc`: 평점 높은 순
    - `like_desc`: 좋아요 많은 순
- [ ] 무한 스크롤 방식으로 리뷰를 불러올 수 있도록 `next_cursor`를 지원한다.

**Request**:

```json
{
  "size": "요청할 리뷰 개수 (기본값: 20)",
  "sort": "정렬 기준 (기본값: created_at_desc)",
  "next_cursor": "다음 페이지 조회를 위한 커서 값 (선택)"
}
```

**Response**:

- 특정 상품의 리뷰 목록과 다음 페이지 조회를 위한 `next_cursor`를 반환한다.

---

## **3. (DELETE) 사용자는 자신의 리뷰를 삭제할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `DELETE /reviews/{review_id}`

- [ ] 사용자는 자신이 작성한 리뷰만 삭제할 수 있다.
- [ ] 리뷰 삭제를 위해 인증된 사용자여야 한다.
- [ ] 삭제된 리뷰는 복구할 수 없다.

**Request**:

```json
{
  "Authorization": "Bearer {token} (필수)"
}
```

**Response**:

- 삭제 성공 여부를 반환한다.

---

## **4. (READ) 사용자는 상품 상세 페이지에서 초기 10개의 리뷰를 조회할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `GET /{product_id}`

- [ ] 사용자는 특정 상품의 리뷰를 초기 화면에서 10개만 조회할 수 있다.
- [ ] 로그인하지 않은 사용자도 조회할 수 있다.
- [ ] 최신순(`created_at_desc`)으로 정렬하여 반환한다.

**Request**:

```json
{
  "size": 10,
  "sort": "created_at_desc"
}
```

**Response**:

- 특정 상품의 최신 10개 리뷰 목록을 반환한다.

---

## **5. (READ) 사용자는 특정 상품의 리뷰를 무한 스크롤 방식으로 조회할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `GET /reviews/{product_id}/allReviews`

- [ ] 사용자는 특정 상품의 리뷰를 무한 스크롤 방식으로 조회할 수 있다.
- [ ] 로그인하지 않은 사용자도 조회할 수 있다.
- [ ] 요청 시, 정렬 기준과 요청할 리뷰 개수를 선택할 수 있다.
- [ ] 정렬 기준:
    - `created_at_desc`: 최신순 (기본값)
    - `rating_desc`: 평점 높은 순
    - `like_desc`: 좋아요 많은 순
- [ ] `next_cursor`를 지원하여 페이지네이션을 처리한다.

**Request**:

```json
{
  "size": "요청할 리뷰 개수 (기본값: 20)",
  "sort": "정렬 기준 (기본값: created_at_desc)",
  "next_cursor": "이전 요청에서 받은 next_cursor 값"
}
```

**Response**:

- 특정 상품의 리뷰 목록과 다음 페이지 조회를 위한 `next_cursor`를 반환한다.

---

## **6. (CREATE) 사용자는 특정 리뷰에 좋아요를 누를 수 있다.**

**Controller**: `ReviewLikeController`  
**Endpoint**: `POST /reviews/{review_id}/reviewLike`

- [ ] 사용자는 특정 리뷰에 좋아요를 누를 수 있다.
- [ ] 리뷰 좋아요를 등록하려면 인증된 사용자여야 한다.
- [ ] 같은 사용자가 동일한 리뷰에 여러 번 좋아요를 누를 수 없다.

**Request**:

```json
{
  "Authorization": "Bearer {token} (필수)"
}
```

**Response**:

- 성공적으로 좋아요가 추가된 경우, 업데이트된 좋아요 개수를 반환한다.

---

## **7. (DELETE) 사용자는 특정 리뷰의 좋아요를 취소할 수 있다.**

**Controller**: `ReviewLikeController`  
**Endpoint**: `DELETE /reviews/{review_id}/like`

- [ ] 사용자는 자신이 좋아요를 누른 리뷰의 좋아요를 취소할 수 있다.
- [ ] 좋아요 취소를 위해 인증된 사용자여야 한다.

**Request**:

```json
{
  "Authorization": "Bearer {token} (필수)"
}
```

**Response**:

- 성공적으로 좋아요가 취소된 경우, 업데이트된 좋아요 개수를 반환한다.

---

## **8. (READ) 사용자는 자신이 작성한 리뷰 목록을 조회할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `GET /api/reviews/myReviews`

- [ ] 사용자는 자신이 작성한 리뷰 목록을 무한 스크롤 방식으로 조회할 수 있다.
- [ ] 사용자의 인증이 필요하다.
- [ ] `lastReviewId`를 기준으로 페이징을 지원한다.

**Request**:

```json
{
  "Authorization": "Bearer {token} (필수)",
  "lastReviewId": "마지막으로 조회된 리뷰 ID (선택)",
  "size": "요청할 리뷰 개수 (기본값: 20)"
}
```

**Response**:

- 사용자가 작성한 리뷰 목록을 반환하며, 추가 데이터가 있는 경우 `next_cursor`를 포함한다.
- 데이터가 더 이상 없으면 빈 배열 (`[]`)을 반환한다.

---
