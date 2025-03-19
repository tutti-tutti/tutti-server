# **공지사항 기능명세서**

1.
    - [ ] (CREATE) 판매자는 공지사항을 등록할 수 있다.
2.
    - [ ] (READ) 사용자는 공지사항의 목록을 조회할 수 있다.
3.
    - [ ] (READ) 사용자는 공지사항의 상세 내용을 조회할 수 있다.
4.
    - [ ] (PUT) 판매자는 공지사항의 내용을 수정할 수 있다.
5.
    - [ ] (DELETE) 판매자는 공지사항을 삭제할 수 있다.

## **1. (CREATE) 판매자는 공지사항을 등록할 수 있다.**

**Controller**: `NoticeController`

**Endpoint**: `POST /notices`

- [ ] 판매자는 공지사항의 제목과 내용을 작성하여 등록할 수 있다.
- [ ] 공지사항 등록 시, 제목(`title`)과 내용(`content`)을 필수로 포함해야 한다.
- [ ] 공지사항은 특정 스토어에 종속될 수 있다.
- [ ] 공지사항 등록은 인증된 회원만 가능하다.
- [ ] 기본적으로 공지사항은 발행 상태로 등록된다.

**Request**:

```json
{
  "title": "공지사항 제목 (필수)",
  "content": "공지사항 내용 (필수)",
  "store_id": "스토어 ID (선택, 스토어 관련 공지인 경우 필수)"
}
```

**Response**:

```json
{
  "id": "생성된 공지사항 ID",
  "title": "공지사항 제목",
  "content": "공지사항 내용",
  "is_published": true,
  "views": 0,
  "created_at": "생성 일시",
  "updated_at": "수정 일시",
  "writer": {
    "id": "작성자 ID",
    "name": "작성자 이름"
  },
  "store": {
    "id": "스토어 ID",
    "name": "스토어 이름"
  }
}
```

---

## **2. (READ) 사용자는 공지사항의 목록을 조회할 수 있다.**

**Controller**: `NoticeController`

**Endpoint**: `GET /notices`

- [ ] 모든 사용자는 공지사항 목록을 조회할 수 있다.
- [ ] 공지사항 목록은 페이징 처리가 되어야 한다.
- [ ] 공개 상태(`isPublished = true`)인 공지사항만 조회할 수 있다.
- [ ] 스토어 ID를 통해 특정 스토어의 공지사항만 필터링할 수 있다.
- [ ] 최신순으로 정렬되어야 한다.

**Request Parameters**:

```
page: 페이지 번호 (선택, 기본값: 1)
size: 페이지 크기 (선택, 기본값: 10)
store_id: 스토어 ID (선택)
keyword: 검색어 (선택)
```

**Response**:

```json
{
  "content": [
    {
      "id": "공지사항 ID",
      "title": "공지사항 제목",
      "created_at": "작성일",
      "updated_at": "수정일",
      "views": "조회수",
      "writer": {
        "id": "작성자 ID",
        "name": "작성자 이름"
      },
      "store": {
        "id": "스토어 ID",
        "name": "스토어 이름"
      }
    }
  ],
  "pageable": {
    "page": "현재 페이지",
    "size": "페이지 크기",
    "total_elements": "전체 공지사항 수",
    "total_pages": "전체 페이지 수"
  }
}
```

---

## **3. (READ) 사용자는 공지사항의 상세 내용을 조회할 수 있다.**

**Controller**: `NoticeController`

**Endpoint**: `GET /notices/{notice_id}`

- [ ] 모든 사용자는 특정 공지사항의 상세 내용을 조회할 수 있다.
- [ ] 상세 조회 시 해당 공지사항의 조회수가 증가한다.
- [ ] 공개 상태(`isPublished = true`)인 공지사항만 조회할 수 있다.
- [ ] 상세 정보에는 제목, 내용, 작성자, 작성일, 수정일, 조회수가 포함되어야 한다.

**Request Parameters**:

```
notice_id: 공지사항 ID (필수)
```

**Response**:

```json
{
  "id": "공지사항 ID",
  "title": "공지사항 제목",
  "content": "공지사항 내용",
  "is_published": true,
  "views": "조회수",
  "created_at": "작성일",
  "updated_at": "수정일",
  "writer": {
    "id": "작성자 ID",
    "name": "작성자 이름"
  },
  "store": {
    "id": "스토어 ID",
    "name": "스토어 이름"
  }
}
```

---

## **4. (PUT) 판매자는 공지사항의 내용을 수정할 수 있다.**

**Controller**: `NoticeController`

**Endpoint**: `PUT /notices/{notice_id}`

- [ ] 판매자는 자신이 작성한 공지사항만 수정할 수 있다.
- [ ] 공지사항 수정 시, 제목(`title`), 내용(`content`), 발행 상태(`isPublished`)를 수정할 수 있다.
- [ ] 수정된 공지사항의 수정일시가 업데이트되어야 한다.

**Request Parameters**:

```
notice_id: 수정할 공지사항 ID (필수)
```

**Request**:

```json
{
  "title": "수정된 공지사항 제목 (선택)",
  "content": "수정된 공지사항 내용 (선택)",
  "is_published": "공개 여부 (선택)"
}
```

**Response**:

```json
{
  "id": "공지사항 ID",
  "title": "수정된 공지사항 제목",
  "content": "수정된 공지사항 내용",
  "is_published": "공개 여부",
  "views": "조회수",
  "created_at": "생성 일시",
  "updated_at": "수정 일시",
  "writer": {
    "id": "작성자 ID",
    "name": "작성자 이름"
  },
  "store": {
    "id": "스토어 ID",
    "name": "스토어 이름"
  }
}
```

---

## **5. (DELETE) 판매자는 공지사항을 삭제할 수 있다.**

**Controller**: `NoticeController`

**Endpoint**: `DELETE /notices/{notice_id}`

- [ ] 판매자는 자신이 작성한 공지사항만 삭제할 수 있다.
- [ ] 시스템 관리자는 모든 공지사항을 삭제할 수 있다.
- [ ] 삭제 시 DB에서 실제로 삭제하거나, 논리적 삭제(soft delete)를 통해 처리할 수 있다.

**Request Parameters**:

```
notice_id: 삭제할 공지사항 ID (필수)
```

**Response**:

- 성공적으로 삭제되면 204 No Content 상태코드를 반환한다.
