# **리뷰 기능명세서**

1.
    - [X]  (CREATE) 사용자는 상품에 대한 리뷰를 작성할 수 있다.
2.
    - [X]  (READ) 사용자는 특정 상품의 리뷰 목록을 조회할 수 있다.
3.
    - [X]  (READ) 사용자는 리뷰의 상세 페이지를 조회할 수 있다.
4.
    - [X]  (READ) 사용자는 자신이 작성한 리뷰 목록을 조회할 수 있다.
5.
    - [X]  (DELETE) 사용자는 자신의 리뷰를 삭제할 수 있다.
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

- [X] 사용자는 특정 상품에 대한 리뷰 목록을 조회할 수 있다.
- [X] 로그인하지 않은 사용자도 조회할 수 있다.
- [X] 요청 시, 정렬 기준과 요청할 리뷰 개수를 선택할 수 있다.
- [X] 정렬 기준:
    - `created_at_desc`: 최신순 (기본값)
    - `rating_desc`: 평점 높은 순
    - `like_desc`: 좋아요 많은 순
- [X] 무한 스크롤 방식으로 리뷰를 불러올 수 있도록 `next_cursor`를 지원한다.

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

- [X] 사용자는 자신이 작성한 리뷰만 삭제할 수 있다.
- [ ] 리뷰 삭제를 위해 인증된 사용자여야 한다.
- [X] 삭제된 리뷰는 복구할 수 없다.

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

## **6. (TOGGLE) 사용자는 특정 리뷰에 좋아요를 누르거나 취소할 수 있다.**

**Controller**: `ReviewLikeController`  
**Endpoint**: `POST /reviews/{review_id}/reviewLike`

---

### 설명

- 사용자는 특정 리뷰에 "도움이 되었어요"를 누를 수 있습니다.
- 이미 좋아요를 누른 리뷰에 다시 요청하면 좋아요가 취소됩니다.
- 즉, **"좋아요 등록"과 "좋아요 취소"를 하나의 API로 처리**하는 **토글(Toggle) 방식**입니다.
- 이 기능은 **로그인한 사용자만** 사용할 수 있습니다.

---

### Request

**Header**

```
Authorization: Bearer {access_token}
```

**Path Parameter**

| 이름          | 타입   | 설명                     |
|-------------|------|------------------------|
| `review_id` | Long | 좋아요를 누르거나 취소할 대상 리뷰 ID |

---

### Response (200 OK)

```json
{
  "reviewId": 123,
  "likeCount": 14,
  "liked": true
}
```

| 필드명         | 타입      | 설명                                                         |
|-------------|---------|------------------------------------------------------------|
| `reviewId`  | Long    | 해당 리뷰 ID                                                   |
| `likeCount` | int     | 현재 좋아요 개수                                                  |
| `liked`     | boolean | 현재 사용자가 해당 리뷰에 좋아요를 누른 상태인지 여부 (`true`: 등록됨, `false`: 취소됨) |

---

### 이전 기능에서 변경된 사항

| 항목           | 이전                            | 변경 후                   |
|--------------|-------------------------------|------------------------|
| 좋아요 등록       | POST /reviews/{id}/reviewLike | 동일 (POST 유지)           |
| 좋아요 취소       | DELETE /reviews/{id}/like     | ❌ 제거됨 (POST에서 취소까지 처리) |
| 같은 사용자 중복 요청 | 400 에러                        | ✅ 요청 시 좋아요 토글됨         |

---

### 프론트엔드 참고사항

- `liked: true` → UI에 "도움이 되었어요" 상태로 표시
- `liked: false` → UI에서 좋아요 표시 제거
- 단일 API로 토글되므로, 상태만 보고 버튼 스타일을 바꿔주세요


