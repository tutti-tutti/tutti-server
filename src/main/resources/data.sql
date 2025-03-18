DELETE
FROM member_agreement_mappings;
DELETE
FROM terms_conditions;
INSERT INTO terms_conditions (terms_type, content, delete_status, created_at, updated_at)
VALUES ('TERMS_OF_SERVICE', '이용약관 내용입니다.', 0, NOW(), NOW()),
       ('PRIVACY_POLICY', '개인정보처리방침 내용입니다.', 0, NOW(), NOW()),
       ('MARKETING_CONTENT', '마케팅 정보 수신 동의 내용입니다.', 0, NOW(), NOW());
DELETE
FROM faqs;
DELETE
FROM faq_categories;
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (1, '계정 관리', '챗봇 지혜 VIP', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (2, '계정 관리', '등록 및 로그인', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (3, '계정 관리', '계정 설정', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (4, '계정 관리', '계정 찾기', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (5, '계정 관리', '계정 사용 불가', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (6, '계정 관리', '지혜pay 계정', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (7, '계정 관리', '데이터 주제 권한', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (8, '검색 & 커뮤니케이션', '커뮤니케이션', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (9, '검색 & 커뮤니케이션', '검색', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (10, '프로모션', '코인', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (11, '프로모션', '쿠폰', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (12, '프로모션', '캠페인', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (13, '주문', '주문', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (14, '주문', '결제', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (15, '주문', '주문 관리', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (16, '배송', '상품출고 대기', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (17, '배송', '상품 미수령 보고', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (18, '애프터서비스', '반품/환불', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (19, '애프터서비스', '무료 반품', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (20, '환불', '환불 절차', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (21, '환불', '환불 안 됨', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (22, '환불', '환불 금액 부족', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (23, '피드백', 'Report Seller', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (24, '피드백', '주문 피드백', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (25, '규정 및 정책', '공지 사항', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (26, '규정 및 정책', '약관', 0);
INSERT INTO faq_categories (id, main_category, sub_category, delete_status)
VALUES (27, '규정 및 정책', '플랫폼 규칙', 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (1, 1, '챗봇 지혜 VIP 멤버십 서비스를 종료할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (2, 1, '챗봇 지혜 VIP 특별품을 구매하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (3, 1, 'VIP 회원 혜택을 구매하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (4, 1, 'VIP 회원 혜택은 어디에서 구매하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (5, 1, '쿠폰이 만료된 후에 사용할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (6, 1, '챗봇 지혜 쿠폰은 전 상품에 적용이 가능한가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (7, 1, '왜 챗봇 지혜 VIP Specials를 받을 수 없었나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (8, 1, '나의 쿠폰을 어디서 찾을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (9, 1, '쿠폰은 얼마나 유효한가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (10, 2, '제가 비밀번호를 잊어버렸습니다.어떻게 재설정할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (11, 2, '내 계정에 로그인할 수 없는 경우 어떻게 해야 합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (12, 2, '챗봇 지혜에 등록하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (13, 2, '실수로 판매자 계정을 등록했습니다. 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (14, 3, '벌칙 및 위반 정보', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (15, 3, '내 계정에 로그인/로그인할 수 없습니다.', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (16, 3, '내 계정을 어떻게 삭제하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (17, 3, '내 계정 로그아웃/계정 전환 방법', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (18, 3, '결제된 주문내역으로 어떻게 확인하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (19, 3, '이메일 알림 구독을 취소하려면 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (20, 3, '계정 확인을 통과하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (21, 3, '등록/로그인 전화번호를 변경하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (22, 3, '한국으로 배송되는 주문의 경우: 한국어를 사용하여 고객님의 배송 주소를 입력하세요.', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (23, 3, '제가 내 계정을 어떻게 관리하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (24, 4, '제가 내 계정을 어떻게 찾을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (25, 5, '내 계정을 사용할 수 없거나 비활성화했습니다.제가 는 무엇을 할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (26, 6, '내 지갑에 몇 개의 은행 계좌를 연결할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (27, 6, '내 계정에 연결한 카드를 어떻게 사용해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (28, 6, '기본 카드를 어떻게 설정할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (29, 6, '내 전자지갑과 연결될 수 있는 은행 계좌 조건에 대한 다른 제한 사항이 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (30, 6, '인출을 위해 내 전자지갑을 제3자 은행 계좌와 연결할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (31, 6, '내 지갑에 넣을 수 있는 최대 금액은 얼마입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (32, 6, '내 지갑을 올리기 위해 수수료가 청구될까요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (33, 6, '왜 내 상위가 실패했나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (34, 6, '내 지갑의 상위 화폐는 어떤 통화로 지정될 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (35, 7, '데이터 주체 권리', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (36, 8, '판매자에게 어떻게 연락하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (37, 8, '판매자가 응답하지 않으면 어떡해요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (38, 9, '제가 어떻게 제품을 찾나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (39, 9, '좋은 판매자를 어떻게 찾을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (40, 10, '2023년 코인월 배송은 어떻게 되나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (41, 10, '코인에 대해 알아야 할 모든 것', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (42, 11, '쿠폰/코드를 적용하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (43, 11, '쿠폰/코드는 어떻게 적용하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (44, 11, '쿠폰이나 할인을 받는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (45, 12, '챗봇 지혜 프로모션에 대해 어떻게 최신 정보를 얻을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (46, 13, '제가 관세 및 수입세를 지불해야 합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (47, 13, '제가 결제 전에 배송 주소를 저장하고 관리하려면 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (48, 13, '제가 송장은 어떻게 받나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (49, 13, '터키에 대한 세금 정책', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (50, 13, '노르웨이의 세금 정책', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (51, 13, '제가 왜 주문할 수 없나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (52, 13, '챗봇 지혜 미국 판매세 면제 프로그램(ASTEP)은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (53, 13, 'ASTEP에 어떻게 등록하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (54, 14, '보너스 관련 질문', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (55, 14, '결제가 실패한 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (56, 14, '결제 후 주문 상태가 ''결제 확인 중''으로 표시되는 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (57, 15, '제가 내 계정에서 내 주문을 찾을 수 없습니다.', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (58, 15, '주문을 취소하려면 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (59, 15, '주문 세부정보(배송지 주소, 색상, 사이즈 등)를 변경하는 방법', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (60, 15, '내 주문이 종료된 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (61, 15, '주문을 어떻게 삭제합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (62, 15, '내 주문이 동결 된 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (63, 15, '내 주문이 보안 이유로 종결된 이유는 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (64, 15, '주문을 받은 후 배송을 어떻게 확인합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (65, 15, '왜 주문 상태가 "결제 진행중"으로 표시되나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (66, 15, '‘X일 배송’ 서비스란 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (67, 16, '제가 주문은 언제 받을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (68, 16, '추적 정보가 업데이트되지 않는 이유는 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (69, 16, '구매자 보호 기간을 연장하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (70, 16, '제 주문이 배송되었는지 어떻게 알 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (71, 17, '환불을 받은 후 상품을 받았습니다. 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (72, 17, '상품을 어떻게 추적할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (73, 17, '왜 신고를 접수할 수 없나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (74, 17, '내 소포를 추적하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (75, 17, '왜 추적 번호가 다른 주문과 중복되는 새 번호로 바뀌었습니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (76, 17, '분쟁 처리 시간이 너무 길고 기다리고 싶지 않은 경우 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (77, 17, '택배를 받지 못했지만 "배달 완료"로 표시되어 있습니다. 어떻게 해야 됩니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (78, 17, '분쟁은 언제 제기될 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (79, 18, '상품을 반품하거나 제품 문제를 해결하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (80, 18, '제가 반품은 어떻게 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (81, 18, '받은 제가 제품이 설명과 일치하지 않습니다.제가 는 무엇을 할 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (82, 18, '반품/환불 요청 결과가 만족스럽지 않습니다.어떻게 할까요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (83, 18, '어떻게 분쟁을 취소합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (84, 19, '반품/환불 요청에 대한 추가 증거를 업로드하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (85, 19, '자세한 카테고리와 항목은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (86, 19, '이 무료 반품 주문의 반품 배송비는 누가 지불합니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (87, 19, '제가 반품 요청을 취소하려면 어떻게 해야 하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (88, 19, '이 무료 반품 라벨을 사용하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (89, 19, '일부 반품 품목을 추가하거나 취소하는 방법은 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (90, 20, '제가 환불은 언제 받을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (91, 20, '환불 내역을 확인하는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (92, 20, '제가 신용카드 환불을 어떻게 확인하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (93, 20, 'Yandex 를 통해 환불을 받는 방법은 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (94, 20, '신용 카드가 더 이상 유효하지 않거나 만료 된 경우 어떻게 환불받을 수 있습니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (95, 20, '신용카드나 직불카드로 어떻게 환불받을 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (96, 21, '환불 문제', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (97, 21, 'ARN이란 무엇인가요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (98, 22, '환불이 부족한 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (99, 22, '주문 페이지에서 확인된 금액보다 환불 금액이 적습니다. 왜 그렇까요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (100, 24, '피드백을 어떻게 남길 수 있나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (101, 24, '내 피드백이 표시되지 않는 이유는 무엇입니까?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (102, 26, 'chatbotjihye.com 개인정보 처리방침', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (103, 26, '사용자 계약서', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (104, 26, 'Jihye 프로모션 약관', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (105, 26, 'Jihye 쿠폰 이용 약관', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (106, 26, '챗봇 지혜 커뮤니티 지침', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (107, 26, '챗봇 지혜 제품 목록 정책', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (108, 27, '미결제 주문에 대해 정책', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (109, 27, '커뮤니케이션에 대한 규칙', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (110, 27, '피드백 조작', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (111, 27, '피드백 왜곡', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (112, 27, '구매자 보호 프로그램 남용에 대한 규칙', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (113, 27, '지적 재산권(IPR) 보호 정책', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (114, 27, '"현지 반품"에 대한 AliExpress 규칙', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (115, 27, '허위 구매', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (116, 27, '지적재산권(IPR) 불만사항을 어떻게 제출하나요?', '답변추후생성', true, 0, 0, 0, 0);
INSERT INTO faqs (id, category_id, question, answer, is_view, view_cnt, positive, negative,
                  delete_status)
VALUES (117, 27, '지적재산권 침해 주장에 대한 집행 조치', '답변추후생성', true, 0, 0, 0, 0);
