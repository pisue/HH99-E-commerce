@startuml
actor User
participant "ProductController" as PC
participant "ProductService" as PS
participant "ProductRepository" as PR

User -> PC: GET /products/{productId}
note right: 사용자가 특정 상품 조회 요청
PC -> PS: getProduct(productId)
note right: Controller가 Service에 상품 ID를 전달하여 조회 요청
PS -> PR: findProductById(productId)
note right: 상품 ID를 통해 상품 정보 조회
PR --> PS: Product
PS --> PC: Product
PC --> User: Product
note right: 특정 상품의 정보를 사용자에게 반환
@enduml
