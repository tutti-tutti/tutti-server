# [장바구니] 기능 요구사항 정의

1. (READ) 사용자는 장바구니에 담긴 모든 상품을 한 눈에 확인할 수 있다.
    -[ ] 사용자가 장바구니 조회 api를 요청하면 delete_status=false 인 상품만 반환한다.
    -[X] request: member의 인증 정보(회원일 경우)~~/session_id(비회원일 경우)~~
    -[X] response: cart_item_id, 상품명, 옵션, 가격, 수량, 품절여부
2. (CREATE) 사용자는 상품을 장바구니에 추가할 수 있다.
    -[ ] 사용자가 장바구니 추가 api를 클릭하면 장바구니 리스트에 판매상품이 추가되어야 한다.
    -[X] request: member의 인증 정보(회원일 경우)~~/session_id(비회원일 경우)~~, product_item_id(상품명, 옵션, 가격, 품절여부),
     수량
    -[X] 그리고 전달 받은 DTO를 DB에 저장하려면 엔티티로 변환해야 한다.
    -[X] response: 없다.
    -[X] 사용자가 동일 상품을 추가할 경우, "장바구니에 동일한 상품이 있습니다. 장바구니에 추가하시겠습니까?" 메세지를 리턴하고 동의한다면 old_product 가
     new_product 로 교체된다.(PUT)
3. (DELETE) 사용자는 상품을 장바구니에 제거할 수 있다.
    -[ ] 회원이 장바구니 상품의 삭제(버튼) api를 요청하면, delete_status=false인 단일 CartItem을 delete_status=true로 업데이트
     한다.
    -[X] request: member의 인증 정보(-> 목록 조회 때문에), cartItemId
    -[X] 해당 상품의 delete_status=true로 update 한다.
    -[X] response: 없다.
    -[ ] delete_status=true 인 CartItem의 장기간 보관 여부에 대해 고민해볼 것
4. (UPDATE) 사용자는 각 상품의 구매 수량을 자유롭게 수정할 수 있다. ---> API가 필요없을지도?
    -[ ] 회원이 장바구니에 담긴 상품의 수량을 수정하고 변경(버튼)를 클릭하면 수량이 변경되도록 한다.
    -[ ] 수량은 1개 이상으로 설정되어야 하며, 수량을 0 이하로 변경하면 시도를 차단하고, 메세지를 리턴합니다. "1개 이상부터 구매하실 수 있습니다."
    -[ ] request: memberId(member의 인증 정보), saleProductId, 구매 수량
    -[ ] 장바구니 상품 목록을 다시 리턴한다.(refresh)
5. 상품 품절 여부에 따라 구매 비활성화 처리를 할 수 있다.
6. 비회원은 회원가입 후에도 장바구니 데이터가 유지된다.
