# **리뷰 기능명세서**

1.
    - [X]  (CREATE) 사용자는 상품에 대한 리뷰를 작성할 수 있다.
2.
    - [ ]  (READ) 사용자는 특정 상품의 리뷰 목록을 조회할 수 있다.
3.
    - [X]  (READ) 사용자는 리뷰의 상세 페이지를 조회할 수 있다.
4.
    - [X]  (READ) 사용자는 자신이 작성한 리뷰 목록을 조회할 수 있다.
5.
    - [ ]  (DELETE) 사용자는 자신의 리뷰를 삭제할 수 있다.
6.
    - [ ]  (CREATE) 사용자는 특정 리뷰의 좋아요를 등록할 수 있다.
7.
    - [ ]  (DELETE) 사용자는 특정 리뷰의 좋아요를 취소할 수 있다.

## **1. (CREATE) 사용자는 상품에 대한 리뷰를 작성할 수 있다.**

**Controller**: `ReviewController`  
**Endpoint**: `POST /reviews`

- [X] 사용자는 주문한 상품에 대한 리뷰를 작성할 수 있다.
- [X] 리뷰 작성 시, 평점(`rating`)과 리뷰 내용(`content`)을 포함해야 한다.
- [ ] 사용자는 리뷰에 이미지를 첨부할 수 있다.(선택)
- [ ] 리뷰 작성 시, 유효한 `order_item_id`가 필요하다.
- [ ] 리뷰를 작성하려면 인증된 사용자여야 한다.

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
- 첫 페이지는 null
- 데이터가 더 이상 없으면 빈 배열 (`[]`)을 반환한다.

```json
{
  "reviews": [
    {
      "review_id": 1,
      "product_id": 123,
      "nickname": "사용자1",
      "content": "배송 빠르고 좋아요!",
      "rating": 5,
      "review_images": [
        "https://image.url/review1.jpg"
      ],
      "like_count": 15,
      "sentiment": "positive",
      "sentiment_probability": 95.2,
      "created_at": "2024-03-16T12:00:00"
    },
    {
      "review_id": 2,
      "product_id": 123,
      "nickname": "사용자2",
      "content": "좋아요!",
      "rating": 4,
      "review_images": [
        "https://image.url/review2.jpg"
      ],
      "like_count": 10,
      "sentiment": "positive",
      "sentiment_probability": 85.3,
      "created_at": "2024-03-15T12:00:00"
    }
  ],
  "next_cursor": "두번째 부터 다음 페이지 조회를 위한 커서 값"
}
```

---

## **3. (READ) 사용자는 리뷰의 상세 페이지를 조회할 수 있다.**

**Controller**: `ReviewApi`
**Endpoint**: `GET /reviews/{review_id}

- [X] 사용자는 리뷰 상세 페이지를 조회할 수 있다.

**Request**:

```json
{
  "ReviewId": "리뷰 ID"
}
```

**Response**:

- 리뷰의 상세 정보를 반환한다.

```json
{
  "review_id": 1,
  "product_id": 123,
  "nickname": "사용자1",
  "content": "배송 빠르고 좋아요!",
  "rating": 5,
  "review_images": [
    "https://image.url/review1.jpg"
  ],
  "like_count": 15,
  "sentiment": "positive",
  "sentiment_probability": 95.2,
  "created_at": "2024-03-16T12:00:00"
}
```

---

## **4. (READ) 사용자는 자신이 작성한 리뷰 목록을 조회할 수 있다.**

**Controller**: `ReviewApi`  
**Endpoint**: `GET /api/reviews/myReviews`

- [X] 사용자는 자신이 작성한 리뷰 목록을 무한 스크롤 방식으로 조회할 수 있다.
- [X] 사용자의 인증이 필요하다.
- [X] `cursor`를 기준으로 페이징을 지원한다.

**Request**:

```json
{
  "Authorization": "Bearer {token} (필수)",
  "cursor": "마지막으로 조회된 리뷰 ID (선택)",
  "size": "요청할 리뷰 개수 (기본값: 20)"
}
```

**Response**:

- 사용자가 작성한 리뷰 목록을 반환하며, 추가 데이터가 있는 경우 `next_cursor`를 포함한다.
- 데이터가 더 이상 없으면 빈 배열 (`[]`)을 반환한다.

- 인증되지 않은 사용자인 경우 (401 Unauthorized)

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "로그인이 필요합니다."
}
```

---

## **5. (DELETE) 사용자는 자신의 리뷰를 삭제할 수 있다.**

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

- 성공적으로 리뷰가 삭제된 경우, 삭제된 리뷰의 ID를 반환한다.

```json
{
  "message": "리뷰가 삭제되었습니다.",
  "review_id": 1
}
```

- 본인이 작성한 리뷰가 아닌 경우 (403 Forbidden)

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "본인이 작성한 리뷰만 삭제할 수 있습니다."
}


```

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
- 이미 좋아요를 누른 리뷰인 경우 (400 Bad Request)

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "이미 좋아요를 누른 리뷰입니다."
}
```

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
- 좋아요를 누르지 않은 상태에서 취소 요청한 경우 (400 Bad Request)

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "좋아요를 누르지 않은 리뷰입니다."
}

```

