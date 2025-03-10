# **[FAQ] 기능 요구사항 정의**

---

## **1. (READ) 사용자는 FAQ 카테고리 목록을 조회할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `GET /api/faqs/categories`

- [ ] 사용자가 FAQ에서 사용 가능한 카테고리 목록을 조회할 수 있다.
- [ ] **Request**: 없음
- [ ] **Response**: 등록된 모든 카테고리 목록을 반환한다.

---

## **2. (READ) 사용자는 FAQ 인기 질문 목록을 조회할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `GET /api/faqs/top`

- [ ] 사용자는 FAQ 인기 질문 목록을 조회할 수 있다.
- [ ] 조회수가 가장 높은 상위 10개의 FAQ를 반환한다.
- [ ] `isView = true`인 데이터만 반환된다.

---

## **3. (READ) 사용자는 FAQ 목록을 조회할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `GET /api/faqs`

- [ ] 사용자가 FAQ 목록 조회 API를 요청하면 `isView = true`인 FAQ만 반환해야 한다.
- [ ] `isView = false`인 데이터는 반환되지 않는다.
- [ ] **Request**:
    - 필수 값 없음
    - 선택적으로 `category`, `subcategory`, `query`를 전달하면 해당 조건에 맞는 FAQ만 조회된다.
    - 기본 페이지 번호: `1`, 기본 페이지 크기: `10`

---

## **4. (READ) 사용자는 특정 FAQ를 조회할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `GET /api/faqs/{faqId}`

- [ ] 사용자가 FAQ 단건 조회 API를 요청하면 `isView = true`인 FAQ만 반환해야 한다.
- [ ] `isView = false`인 데이터는 반환되지 않는다.
- [ ] 사용자가 FAQ를 조회하면 `viewCnt`가 `1` 증가한다.

---

## **5. (UPDATE) 사용자는 특정 FAQ의 조회수를 증가시킬 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `PATCH /api/faqs/{faqId}/view`

- [ ] 사용자가 FAQ를 조회하면 `viewCnt`가 자동으로 증가해야 한다.

---

## **6. (READ) 사용자는 특정 키워드를 포함하는 FAQ를 검색할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `GET /api/faqs/search`

- [ ] 사용자가 검색어를 입력하면 해당 키워드를 포함하는 FAQ만 반환해야 한다.
- [ ] `isView = true`인 데이터만 조회된다.
- [ ] `isView = false`인 데이터는 반환되지 않는다.
- [ ] 기본 페이지 번호: `1`, 기본 페이지 크기: `20`

---

## **7. (UPDATE) 사용자는 특정 FAQ에 피드백을 반영할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `PATCH /api/faqs/{faqId}/feedback`

- [ ] 사용자가 특정 FAQ에 긍정/부정 피드백을 남길 수 있다.
- [ ] `positive = true`일 경우 `positive 카운트` 증가
- [ ] `positive = false`일 경우 `negative 카운트` 증가

---

## **8. (CREATE) 관리자는 FAQ를 등록할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `POST /api/faqs`

- [ ] 관리자가 FAQ 등록 API를 호출하면 FAQ가 저장된다.
- [ ] `isView` 필드를 설정할 수 있으며, 기본값은 `false`이다.
- [ ] `viewCnt`는 기본값 `0`으로 설정된다.

---

## **9. (UPDATE) 관리자는 FAQ를 수정할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `PUT /api/faqs/{faqId}`

- [ ] 관리자가 기존 FAQ를 수정할 수 있다.
- [ ] `isView` 필드도 수정 가능하다.

---

## **10. (DELETE) 관리자는 FAQ를 삭제할 수 있다.**

**Controller**: `FaqController`  
**Endpoint**: `DELETE /api/faqs/{faqId}`

- [ ] 관리자가 FAQ 삭제 API를 호출하면 해당 FAQ는 `deleteStatus = true`로 변경된다.
- [ ] 삭제된 FAQ의 `isView` 값은 `false`로 변경된다.
- [ ] 삭제된 FAQ는 목록에서 조회되지 않는다.
- [ ] 삭제는 **소프트 삭제** 방식으로 처리된다.

---


