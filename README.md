# pedido-pagamento
Exemplo de app com Spring Cloud: Service Discovery, API Gateway, Open Feign, Circuit Braker, fallback

### Contextos:
- Serviço pagamentos: dado um pedido, é selecionado realizar pagamento, se o serviço de pedido está normal o pagamento é confirmado no serviço de pedido. Caso contrário existe um fallback que informa que pagamento foi recebido mas não integrado.
- Serviço pedidos: dado um pedido com uma lista de itens, o 'pedidos' se comunica com o serviço de pagamentos para efetivar a compra.

## Tecnologias
- Spring Cloud 2021.0.5
- Spring boot 2.7.9
- java 17
- Maven 4.0.0
- MySQL 8.0
- Docker
- Lombok
- Flyway - Migrations

#### Executano a API
Para este projeto é necessário ter o docker, maven, java 17 instalados.



#### *Done List*
- Serviços *pagamentos* e *pedidos*
- Eureka Server
- Balanceamento de carga
- API Gateway
- Circuit Breaker
- fallback
<img width="358" alt="image" src="https://user-images.githubusercontent.com/42658870/223105030-146d4c1e-2a81-4226-8bad-036124f80146.png">

#### *To Do List*
- Server Config
- Infra
- Auth Server
- Micrometer Tracing
- Swagger
- Testes
- Sonar
- Jenkins
