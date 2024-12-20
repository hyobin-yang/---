# '편의점' 기능명세서

### 1. 재고 관리 기능
- 각 상품의 재고 수량을 고려하고 결제 가능 여부 확인 [✅]
  - 수량 부족할 때 결제 불가
- 상품의 구매가 완료되어 영수증을 출력하는 시점에 결제된 수량만큼 재고 차감 [✅]

### 2. 프로모션 할인 기능
- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용
  - 구매 시 프로모션 상품 날짜 검사 [✅]
  - 기간이 지났다면 프로모션 수량을 일반 수량으로 이월하고 결제한다 [✅]
  - 프로모션 날짜 검사는 상품에 프로모션이 존재하는지 확인할 때마다 진행 [✅]
- 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행
  - 1+1 또는 2+1 프로모션 존재
  - 한 상품에는 한 프로모션만 적용
- 프로모션 혜택은 프로모션 재고 내에서만 적용 가능
- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용
  - 프로모션 적용되는 상품은 무조건 프로모션 수량부터 차감 [✅]
- 프로모션 적용이 가능한 상품을 덜 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내 [✅]
  - 혜택 수용 O: 구매 수량 업데이트 [✅]
  - 혜택 수용 X: 원래 수량대로 결제 진행 [✅]
- 프로모션 재고가 부족할 경우 일부 수량에 대해 정가로 결제하게 됨을 안내 [✅]
  - 정가 결제 수용 O: 그대로 결제 진행 [✅]
  - 정가 결제 수용 X: 정가로 결제해야 할 상품 수량만큼 차감 [✅]

- 프로모션 모델
  - 프로모션 이름
  - 1개를 무료 증정받기 위해 구매해야 할 수량(N): 문제에서 N + 1로 제한하였으므로 1은 고정시킨다

### 3. 멤버십 할인
- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다. [✅]
  - 30퍼센트는 고정
- 프로모션 적용 후 남은 금액🚨에 대해 멤버십 할인을 적용한다. [✅]
  - 반드시 프로모션 적용 후 '남은 금액'에 대해 할인을 적용해야 한다
- 멤버십 할인의 최대 한도는 8,000원이다.
  - 할인받을 수 있는 금액이 8000원을 넘으면 8000원만 할인

### 4. 영수증 출력 기능
- 구매 상품 내역: 구매한 상품명, 수량, 가격
- 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
- 금액 정보
    - 총구매액: 구매한 상품의 총 수량과 총 금액
    - 행사할인: 프로모션에 의해 할인된 금액
    - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    - 내실돈: 최종 결제 금액

- 영수증 출력을 위한 dto 생성

### 5. 입출력
- 파일 입출력
  - 상품: name,price,quantity,promotion 형태
    - 프로모션 상품: promotion이 존재
    - 일반 상품: promotion이 null -> null 처리 주의 🚨
    - 프로모션 상품과 일반 상품 처리 -> 하나의 상품 모델에 프로모션 수량과 일반 수량을 함께 둬서 관리(파일 읽을 때 저장 주의 🚨)
  - 프로모션: name,buy,get,start_date,end_date 형태

- 프로모션 상품 추가 여부 입력
  - 현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)

- 할인 적용 불가한 프로모션 상품 구매 여부 입력
  - 현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)

- 멤버십 할인 적용 여부 입력
  - 멤버십 할인을 받으시겠습니까? (Y/N)

- 프로그램 종료 여부 입력
  - 감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)

### [ERROR] 예외 상황 + 재입력 로직

- 구매할 상품과 수량 형식이 올바르지 않은 경우
  - [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.

- 존재하지 않는 상품을 입력한 경우
  - [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.

- 구매 수량이 재고 수량을 초과한 경우
  - [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.

- 기타 잘못된 입력의 경우
  - [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.

### 기능 작동 흐름
1) 편의점 안내 문구 + 재고 출력
2) 구매할 상품명, 수량 입력 (예: [사이다-2],[감자칩-1])
3) if needed) 더 가져올 수 있는 수량 가져올 건지 여부 입력
4) if needed) 프로모션 할인 적용되지 않아도 구매할 건지 여부 입력
5) 멤버십 할인 받을 건지 여부 입력
6) 영수증 출력
7) 프로그램 종료할 건지 여부 입력

### 구현할 것들
- Item 모델 [✅]
  - 상품명, 가격, 일반 수량, 프로모션 수량, 프로모션 리스트(+1개임을 검사하는 로직)
- 프로모션 모델 [✅]
  - 프로모션 이름, N, 1, 시작일, 종료일
- 멤버십 enum
  - 30% 
  - 8000원
  - 할인 계산 로직 [✅]
- 영수증 dto
- 재고 레포지토리
  - Item 모아두기
- 주문한 상품 dto [✅]
  - 상품명, 구매할 수량
- 구매한 상품 모델 
  - Item, 구매한 총수량, 증정 수량 
- 구매한 상품 레포지토리
  - 구매 상품들 저장
- 대답 enum [✅]
  - Y, N 검사 및 값 반환

[✅]: 구현 체크
[🚨]: 주의할 점