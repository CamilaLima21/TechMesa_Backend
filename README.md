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

A modelagem de dados proposta estrutura a relação entre várias entidades essenciais no contexto de um sistema para gestão de restaurantes. Estas entidades incluem **endereços**, **clientes**, **restaurantes**, **horários de funcionamento**, **avaliações**, **reservas** e **mesas de restaurante**. 

1. **Endereços**: Esta entidade centraliza informações geográficas e é compartilhada entre os clientes e os restaurantes. Cada cliente e restaurante está associado a um endereço único, contendo atributos como rua, cidade, estado e código postal.

2. **Clientes**: Representa as pessoas que utilizam a plataforma para fazer reservas ou avaliar os restaurantes. A entidade de clientes mantém informações como nome, e-mail, telefone e, possivelmente, preferências pessoais.

3. **Restaurantes**: Agrupa dados relativos aos estabelecimentos, como nome, tipo de cozinha, contato, e possui uma relação direta com a entidade **endereços** para localizar cada restaurante.

4. **Horário de Funcionamento**: Relacionado aos **restaurantes**, essa entidade define os dias da semana e horários em que o restaurante está aberto. Inclui atributos como hora de abertura, hora de fechamento e especificações para feriados.

5. **Avaliações**: Esta entidade conecta **clientes** e **restaurantes** para registrar feedback. Cada avaliação contém informações como classificação (em estrelas), comentários e data da avaliação, permitindo monitorar a experiência dos clientes.

6. **Reservas**: Representa as interações entre **clientes** e **mesas de restaurante** em um restaurante específico. Inclui informações como data, hora, número de pessoas e status da reserva (confirmada, cancelada, etc.).

7. **Mesas de Restaurante**: Modela os recursos físicos disponíveis em um restaurante. Cada mesa tem atributos como capacidade de assentos e número da mesa, e está associada a um restaurante. Essa entidade se conecta às **reservas** para gerenciar disponibilidade.

A integração destas entidades garante um fluxo lógico e organizado de informações, permitindo consultar facilmente quais clientes fizeram reservas, como avaliaram os restaurantes, e verificar horários e disponibilidade. Essa abordagem também favorece a escalabilidade e consistência do sistema ao expandir o número de restaurantes ou clientes.

Abaixo segue a diagrama da modelagem de dados relacional do sistema:

![image](https://github.com/user-attachments/assets/109de175-413e-4abf-90ad-e98555290ad9)

![image](https://github.com/user-attachments/assets/f56e36e8-4c2f-4904-b035-e62dcc080be3)



## Estrutura do Projeto
O projeto está organizado nas seguintes camadas:

  - domain: Define as entidades principais do domínio.
  - gateway: Interfaces e implementações para interação com o banco de dados.
  - usecase: Contém os casos de uso com a lógica de negócios.
  - infrastructure.persistence.entity: Representa as entidades de persistência do banco de dados.
  - infrastructure.persistence.repository: Interfaces dos repositórios Spring Data JPA.
  - usecase.exception: Exceções customizadas utilizadas nos casos de uso.

## Como Executar

Para configurar a API, basta clonar o projeto: 

https://github.com/CamilaLima21/TechMesa_Backend.git

### Criar e Executar uma Imagem Docker: 

•	No terminal, na raiz do projeto, execute o seguinte comando para construir a imagem Docker: 
**docker build --tag techmesa .**

•	Após a construção da imagem, execute um contêiner Docker com o seguinte comando: 
**docker run --name techmesadocker -p 8080:8080 techmesa**
 
•	É possível visualizar o Swagger da aplicação neste endereço: 
http://localhost:8080/swagger-ui/index.html#/ 
 
### Utilizar uma imagem Docker pública, no Registry do Docker: 

•	É possível acessar a imagem pública através deste endereço: 
	[docker.io/camiladck23/techmesa] 
 
### Utilizar um Deploy Publicado em uma Plataforma Gratuita: 

•	Acessar a API pública atráves da URL abaixo: 
 https://techmesa-backend.onrender.com/swagger-ui/index.html#/

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

JDBC URL: jdbc:h2:mem:techmesa

USERNAME=sa

PASSWORD=

## Collection POSTMAN

A collection postman está disponível na raiz do projeto através do arquivo postman.json

## Licença
Este projeto é licenciado sob a Licença MIT.

## Contato
Camila Marques de Lima - cml.isa.17@gmail.com

Eduardo Bento Nakandakare - nakandakare9@gmail.com



