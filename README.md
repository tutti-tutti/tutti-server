# 오늘의 Tutti <img src="https://github.com/user-attachments/assets/56eb34bd-9282-415b-b5c9-1f2e93f89ad3" align="left" width="100"/>

🎵 당신의 완벽한 쇼핑을 위해 도와드릴게요!

<br/>

![뚜띠_표지](https://github.com/user-attachments/assets/08c1cfef-3c2a-4098-8caf-21c9dc27a50e)

---

## 🎵 Tutti's BE Developers

## 👨‍💻 Team Tutti Developers

<table>
  <tr align="center">
    <td><img src="https://github.com/your-image-path/member1.png" width="100px" /></td>
    <td><img src="https://github.com/your-image-path/member2.png" width="100px" /></td>
    <td><img src="https://github.com/your-image-path/member3.png" width="100px" /></td>
    <td><img src="https://github.com/your-image-path/member4.png" width="100px" /></td>
    <td><img src="https://github.com/your-image-path/member5.png" width="100px" /></td>
  </tr>
  <tr align="center">
    <td><a href="https://github.com/member1"><b>팀장 신동준</b></a></td>
    <td><a href="https://github.com/member2"><b>팀원 안정민</b></a></td>
    <td><a href="https://github.com/member3"><b>팀원 김가영</b></a></td>
    <td><a href="https://github.com/member4"><b>팀원 김혜미</b></a></td>
    <td><a href="https://github.com/member5"><b>팀원 이보석</b></a></td>
  </tr>
</table>


## ✅ 코드 작성 및 PR 규칙

- 초반에는 코드 컨벤션을 참고하여 작성해주세요.
- PR을 올리기 전에는 **항상 서버를 실행**해보고 정상 동작하는지 확인해주세요.
- PR에 달린 리뷰는 **당일 내로 해결**해주세요.
- 리뷰에 대한 **리졸브(Resolve)는 리뷰 작성자**가 직접 눌러주세요.

---

## ✅ 코드 컨벤션

### 1. 테이블명
- 복수형 + `snake_case` 사용  
  예시: `users`, `order_histories`, `product_items`

### 2. 컬럼명
- 단수형 + 소문자 + `snake_case` 사용  
  예시: `user_id`, `created_at`, `order_status`

### 3. 클래스명 / 메서드명
- 단수형 + `camelCase` 사용  
  예시: `OrderService`, `createOrder()`

### 4. Boolean 타입
- `1(true)` / `0(false)` 값으로 저장

### 5. Enum 사용
- `@Enumerated(EnumType.STRING)` 어노테이션 사용

### 6. DTO
- Java `record` 클래스 사용 (Java 14+)

### 7. Setter 금지
- [Setter 사용 지양 참고 자료](https://velog.io/@langoustine/setter-지양-이유)


## ✅ Git 컨벤션

### 🔸 커밋 컨벤션

#### ✅ Type

| 타입     | 설명                     |
|----------|--------------------------|
| `feat`   | 새로운 기능 추가         |
| `docs`   | 문서 수정                |
| `style`  | 코드 포맷팅, 세미콜론 등 비기능적 변경 |
| `refactor` | 코드 리팩토링           |

#### ✅ Subject

- 제목은 **50자 이내**로, 마침표 및 특수기호 ❌  
- 영문으로 작성 시: **동사 원형 + 대문자 시작**  
- **개조식 구문** 사용 → 완전한 문장 대신 핵심만 간결하게 작성

#### ✅ Body

- 한 줄 당 **72자 이내**
- **변경 이유** 또는 **무엇을 변경했는지**를 상세하게 작성
- 예시:
  ```text
  feat: 삭제 여부 도입에 따른 장바구니 상품 조회 기능 수정
  1. CartItemRepository.java 메서드 수정: delete_status=false 조건 추가





