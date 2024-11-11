# 🏪편의점
이 프로젝트는 구매자의 할인 혜택과 재고 관리를 고려하여 최종 결제 금액을 산출하고 결제할 수 있는 콘솔 기반의 결제 시스템입니다.

## 주요 기능
- 상품 목록 조회: 재고 및 프로모션 정보를 포함한 상품 목록을 표시합니다.
- 구매 및 결제 금액 계산: 구매하려는 상품과 수량을 입력하여 최종 결제 금액을 산출합니다.
- 할인 적용:
  - 프로모션 할인: 특정 조건에 따라 'N개 구매 시 1개 무료'와 같은 프로모션을 적용합니다.
  - 멤버십 할인: 프로모션 할인 후 남은 금액에 대해 30% 할인을 적용하며, 할인 최대 한도는 8,000원입니다.
- 재고 관리: 결제된 수량만큼 재고를 차감하며, 프로모션 재고와 일반 재고를 각각 관리합니다.
- 영수증 출력: 구매 내역과 결제 세부 정보가 포함된 영수증을 출력하여 고객에게 안내합니다.
- 추가 구매: 결제 후 추가 구매 의사를 확인하고, 추가 구매 시 최신 재고 정보를 표시합니다.

## 패키지 구조
```
📦store
 ┣ 📂controller
 ┃ ┗ 📜PurchaseController.java
 ┣ 📂domain
 ┃ ┣ 📜Product.java
 ┃ ┗ 📜Promotion.java
 ┣ 📂dto
 ┃ ┣ 📜ProductDto.java
 ┃ ┣ 📜PromotionDto.java
 ┃ ┗ 📜PurchaseDto.java
 ┣ 📂enums
 ┃ ┣ 📜Constants.java
 ┃ ┣ 📜ExceptionMessage.java
 ┃ ┣ 📜Message.java
 ┃ ┗ 📜ProductConstants.java
 ┣ 📂repository
 ┃ ┣ 📜ProductRepository.java
 ┃ ┗ 📜PromotionRepository.java
 ┣ 📂service
 ┃ ┗ 📜PurchaseService.java
 ┣ 📂util
 ┃ ┗ 📜Validator.java
 ┣ 📂view
 ┃ ┣ 📜InputView.java
 ┃ ┗ 📜OutputView.java
 ┗ 📜Application.java
 ```
- `PurchaseController` : 애플리케이션의 주요 흐름을 관리하며 사용자 요청을 처리합니다.
- `Product`: 상품 정보를 관리하는 클래스입니다. 프로모션 적용에 따라 재고를 관리합니다. 
- `Promotion`: 프로모션 정보를 관리하는 클래스입니다.
- `ProductDto`, `PromotionDto`, `PurchaseDto`: 데이터 전달을 위한 DTO 클래스입니다.
- `Constants`, `ExceptionMessage`, `Message`, `ProductConstants`: 애플리케이션에서 사용하는 상수 및 메시지 관리합니다.
- `ProductRepository`: 상품 정보를 저장하고 조회하는 리포지토리입니다.
- `PromotionRepository`: 프로모션 정보를 저장하고 조회하는 리포지토리입니다.
- `PurchaseService`: 비즈니스 로직을 처리하며 할인 정책, 결제 금액 계산 등을 수행합니다.
- `Validator` : 입력값의 유효성을 검사하여 잘못된 입력 시 `IllegalArgumentException`을 발생시킵니다.
- `InputView`, `OutputView` : 콘솔 입출력을 담당합니다.
- `Application` : `PurchaseController`를 호출하여 애플리케이션을 시작하는 메인 클래스입니다.


## 기능 목록
- **상품 목록 출력**
  - [x] 모든 상품 정보(상품명, 가격, 재고, 프로모션)를 출력 기능
  - [x] `Product`의 `toString` 메서드를 형식에 맞게 오버라이드
- **상품명과 수량 입력 및 검증**
  - [x] 상품명, 수량 입력 기능
  - [x] 상품명, 수량 추출 기능
  - [x] 입력 형식 검증 (예: [사이다-2],[감자칩-1])
  - [x] 보유 상품 외 입력 검증
  - [x] 수량 재고 초과 검증
- **상품과 프로모션 데이터 저장**
  - [x] `products.md`에서 데이터를 추출하여 `List<Product>`로 저장 기능
  - [x] `promotions.md`에서 데이터를 추출하여 `List<Promotion>`으로 저장 기능
- **재고 관리**
  - [x] 프로모션 적용 시 프로모션 재고 우선 감소 기능
  - [x] 프로모션 미적용 시 일반 재고 감소 기능
- **프로모션 적용**
  - [x] 현재 날짜가 프로모션 기간인지 확인 기능
  - [x] 프로모션 재고가 충분한 경우 추가 증정 수량 계산 기능
  - [x] 프로모션 재고가 부족한 경우 나머지 수량 계산 기능
  - [x] 증정 상품 제공이 가능할 경우 추가 여부 입력 기능
    - Y: 증정 상품 추가
    - N: 추가 안함
  - [x] 프로모션 재고가 부족할 경우 나머지 수량 정가 결제 여부 입력 기능
    - Y: 일부 수량은 정가로 결제
    - N: 일부 수량은 제외하고 결제
- **멤버십 적용**
  - [x] 멤버십 적용 여부 입력 기능
    - Y: 멤버십 할인 적용
    - N: 멤버십 할인 적용 안함
  - [x] 프로모션 미적용 금액의 30% 할인 기능
  - [x] 멤버십 할인 한도 설정 기능 (8,000원 초과 시 8,000원으로 제한)
- **결제 금액 계산**
  - [x] 총구매수량 계산 기능
  - [x] 총구매액 계산 기능 (구매 상품의 가격*수량의 합)
  - [x] 프로모션 할인 금액 계산 기능 (증정 상품의 가격*수량의 합)
  - [x] 최종 결제 금액 계산 기능 (총구매액 - 프로모션 할인 - 멤버십 할인)
- **추가 구매 및 종료**
  - [x] 추가 구매 여부 입력 기능
    - Y: 재고가 갱신된 목록 확인 후 추가 구매
    - N: 구매 종료
