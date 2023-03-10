# pedido-pagamento
Exemplo de app com Spring Cloud: Service Discovery, API Gateway, Open Feign, Circuit Braker, fallback

### Contextos e services:
> - **Server**: Projeto que serve como service discovery, utilizando Eureka Server. Após a sua execução, o acesso é feito por meio do endereço http://localhost:8761;</br>
> - **Gateway**: Projeto que atua como API Gateway, também utilizando ferramentas do Spring Cloud;</br>
> - **pagamento**: Microsserviço de pagamentos com banco de dados MySQL. Após executar o Server, o Gateway e esse projeto, o acesso é feito por meio do endereço http://localhost:8082/pagamentos-ms/pagamentos. Dado um pedido, é selecionado realizar pagamento, se o serviço de pedido está normal o pagamento é confirmado no serviço de pedido. Caso contrário existe um fallback que informa que pagamento foi recebido mas não integrado.</br>
> - **pedido**: Microsserviço de pedidos com banco de dados MySQL. Após executar o Server, o Gateway e esse projeto, o acesso é realizado pelo endereço http://localhost:8082/pedidos-ms/pedidos; Dado um pedido com uma lista de itens, o 'pedidos' se comunica com o serviço de pagamentos para efetivar a compra.

## Tecnologias
- Spring Cloud 2021.0.5
- Spring boot 2.7.9
- java 17
- Maven 4.0.0
- MySQL 8.0
- Docker
- Lombok
- Flyway - Migrations

### Executano a API
Requisitos mínimos: Maven, Java 17, MySQl 8.

Após clonar o projeto: `git clone https://github.com/marleiSilveira/pedido-pagamento.git`

Entre nos diretórios (sendo que o *Server* deve ser o primeiro a ser executado) e execute em cada um deles com: `mvn spring-boot:run`

Diretorios para executar: 

> server</br>
> gateway</br>
> pedido</br>
> pagamento</br>

Após executar a aplicação, para visualizar o Eureka Server acesse: 

> http://localhost:8761/

Deverá aparecer as seguintes instâncias:

<img width="665" alt="image" src="https://user-images.githubusercontent.com/42658870/223123448-67ef45c0-e8bc-4cfd-b61c-461dbe573c40.png">

> O Spring Cloud Gateway, obtém a lista de endereços de todos os serviços registrados no Eureka Server, configura uma rota dinâmica para esses serviços e já faz o balanceamento de carga nas requisições.

Para executar mais instâncias do 'pedido' e ver o balanceamento de carga em ação execute no powershel o comando: 
`& ".\pedidos\mvnw.cmd" spring-boot:run -f ".\pedidos\pom.xml"`

Após isso envie uma requisição para: 

> http://localhost:8082/pedidos-ms/pedidos/porta. 

A cada envio, observe no powershel que ele já exibe a mensagem "Requisição respondida pela instância executando na porta <xxxxx>". Selecionando novamente o botão "Send" aparece a porta <yyyyy>, a cada vez que enviarmos observe que ele vai trocando as portas.
  
#### Endpoints para testar:

### pedidos 
````
### Verificar porta do balanceamento de carga
GET http://localhost:8082/pedidos-ms/pedidos/porta

### Buscar pedidos registrados
GET http://localhost:8082/pedidos-ms/pedidos

### Buscar pedido id = 1
GET http://localhost:8082/pedidos-ms/pedidos/1

### Realizar um pedido
POST http://localhost:8082/pedidos-ms/pedidos
Content-Type: application/json

{
  "itens": [
    {
      "quantidade": 1,
      "descricao": "livro java"
    },
    {
      "quantidade": 2,
      "descricao": "caneta bic"
    }
  ]
} 
````

### pagamentos: 
```
### Verificar status de pagamento do pedido id = 1
GET http://localhost:8082/pagamentos-ms/pagamentos/1

### Confirmar pagamento do pedido id = 1. É executado quando o pagamento se comunica com o pedido. Retorna pagamento confirmado ou FallbackMethod.
PATCH http://localhost:8082/pagamentos-ms/pagamentos/1/confirmar 
```

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

#### Melhorias
Atualmente quando o serviço de *pagamento* recebe o pagamento e confirma, manda mensagem para o serviço de *pedido* que está esperando a confirmação para finalizar o pedido.

No serviço de *pagamento* foi colocado uma interface com o FeignClient indicando o serviço de *pedido* e no *@service* de *pagamento* é feito uma chamada para o FeignClient para confirmar o pagamento.

Foi necessário implementar um método Fallback para caso o pedido não estivesse operando.

##### Problema
- transferência de responsabilidade, pois quando *pedido* esta fora do ar o estatus do pagamento altera para **confirmado_sem_integração**, para mais tarde integrar ao pedido.
- acoplamento, os serviços se comunicam diretamente entre si.

##### Solução
Colocar um *Message Broker* na camada intermediária entre os serviços.

<img width="353" alt="image" src="https://user-images.githubusercontent.com/42658870/223176438-f8bbbbab-2c79-4665-8681-2985ac4bb6d0.png">

[Aqui](https://github.com/marleiSilveira/rabbitmq-pedido-pg) foi feito a implementação do RabbitMQ para a comunicação entre os serviços.




