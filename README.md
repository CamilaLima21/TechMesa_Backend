# TechMesa_Backend

O Sistema de Reserva e Avaliação de Restaurantes – TechMesa, é uma aplicação web desenvolvida em Java, com o propósito de facilitar e otimizar o processo de reservas tanto para clientes quanto para restaurantes. 

Essa documentação tem como objetivo apresentar o projeto, as requisições e modelo de dados utilizados. 

## Tecnologias Utilizadas

  - Event Storming: Miro;  
  - Linguagem: JAVA 17;  
  - Framework: Spring Boot, Spring JPA, Hibernate, Lombok; 
  - Repositório: GitHub; 
  - Banco de dados: H2 DataBase; 
  - Publicação: Docker, Render; 
  - Testes: JUnit, Mockito;
  - Dependências: Maven; 

## Funcionalidades

  - Cadastro de Clientes
  - Cadastro de Endereços
  - Cadastro de Restaurantes
  - Gerenciamento de Reservas
  - Avaliação e Comentários

## Event Storming

Para visualizar a fase final e o resultado obtido durante o desenvolvimento do Event Storming, acesse o link abaixo:

https://miro.com/app/board/uXjVIPDJL4I=/

![image](https://github.com/user-attachments/assets/b9d339b5-78f8-450a-9d0e-f15b3014186a)


## Modelo de Dados

## Estrutura do Projeto
O projeto está organizado nas seguintes camadas:

  - domain: Define as entidades principais do domínio.
  - gateway: Interfaces e implementações para interação com o banco de dados.
  - usecase: Contém os casos de uso com a lógica de negócios.
  - infrastructure.persistence.entity: Representa as entidades de persistência do banco de dados.
  - infrastructure.persistence.repository: Interfaces dos repositórios Spring Data JPA.
  - usecase.exception: Exceções customizadas utilizadas nos casos de uso.

## Como Executar

## Recursos

### Address - Endereços
- Cadastrar Endereço: POST /addresses
- Consultar Endereço: GET /addresses/{addressId}
- Editar um Endereço: PUT /addresses/{addressId}
- Deletar um Endereço: DELETE /addresses/{addressId}

### Clients - Clientes
- Cadastrar Cliente: POST /clients
- Consultar Cliente: GET /clients/{clientId}
- Editar um Cliente: PUT /clients/{clientId}
- Deletar um Cliente: DELETE /clients/{clientId}
- Consultar Cliente por parte do nome: /clients/{partName}
- Consultar Cliente por nomme: /clients/{name}
- Consultar Cliente por email: /clients/{email}

### OpeningHours - Horário Funcionamento
- Cadastrar um Horário: POST /openingHours
- Consultar um Horário: GET /openingHours/{oeningHoursId}
- Consultar todos os Horários: GET /openingHours
- Editar um Horário: PUT /openingHours/{oeningHoursId}
- Deletar um Horário: DELETE /openingHours/{oeningHoursId}

### Ratings - Avaliações
- Cadastrar uma Avaliação: POST /ratings
- Consultar uma Avaliação: GET /ratings/{ratingsId}
- Consultar todos as Avaliações: GET /ratings
- Editar uma Avaliação: PUT /ratings/{ratingsId}
- Deletar uma Avaliação: DELETE /ratings/{ratingsId}

### Reserves - Reservas
- Cadastrar uma Reserva: POST /reserves
- Consultar uma Reserva: GET /reserves/{reservesId}
- Consultar todos as Reservas: GET /reserves
- Consultar as Reservas por data e Id do Restaurante: GET /reserves/{dateReserve}/{restaurantId}
- Editar uma Reserva: PUT /reserves/{reservesId}
- Deletar uma Reserva: DELETE /reserves/{reservesId}

### Restaurants - Restaurantes
- Cadastrar um Restaurante: POST /restaurants
- Consultar um Restaurante: GET /restaurants/{restaurantsId}
- Consultar todos os Restaurantes: GET /restaurants
- Consultar restaurante por tipo de cozinha: GET /restaurants/{typeKitchen}
- Consultar restaurante por parte do nome: GET /restaurants/{partName}
- Consultar restaurante por nome: GET /restaurants/{name}
- Consultar restaurante por email: GET /restaurants/{email}
- Consultar restaurante por cidade: GET /restaurants/{city}
- Consultar restaurante por cidade e bairro: GET /restaurants/{city}/{neighborhood}
- Editar um Restaurante: PUT /restaurants/{restaurantsId}
- Deletar um Restaurante: DELETE /restaurants/{restaurantsId}

### TableRestaurants - Mesas Restaurantes
- Cadastrar uma Mesa Restaurante: POST /table-restaurants
- Consultar uma Mesa Restaurante: GET /table-restaurants/{id}
- Consultar todas as Mesas Restaurantes: GET /table-restaurants/{page}/{size}
- Consultar uma Mesa Restaurante por id, status da ocupação e data da reserva: GET /table-restaurants/{id}/{statusTableOccupation}/{dateReserve}
- Consultar uma Mesa Restaurante por Nome da Mesa e id da reserva: GET /table-restaurants/{tableIdentification}/{reserveid}
- Editar uma Mesa Restaurante: POST /table-restaurants/{table-restaurant-id}
- Deletar uma Mesa Restaurante: POST /table-restaurants/{id}

## Documentação Swagger

Com a aplicação rodando: http://localhost:8080/swagger-ui/index.html#/

## Pré-Requisitos
- Docker
- Java 17
- Maven 3.6+
- IDE como IntelliJ IDEA ou Eclipse

## Console H2
Com a aplicação rodando: http://localhost:8080/h2-console

JDBC URL: 

USERNAME=sa

PASSWORD=

## Collection POSTMAN

## Licença
Este projeto é licenciado sob a Licença MIT.

## Contato
Camila Marques de Lima - cml.isa.17@gmail.com

Eduardo Bento Nakandakare - nakandakare9@gmail.com



