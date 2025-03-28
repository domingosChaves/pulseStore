# PulseStore

Este projeto é uma aplicação de e-commerce desenvolvida em Java com Spring Boot. O foco principal é implementar um sistema de checkout para permitir que os usuários façam compras online e gerem documentação fiscal.

## Arquitetura do Projeto

Utilizamos a arquitetura MVC (Model-View-Controller), que é uma prática comum para organizar aplicativos Spring Boot. A estrutura do projeto é dividida em três camadas principais:

- **Controller**: Gerencia as requisições HTTP e define os endpoints da API.
- **Service**: Contém a lógica de negócios e manipulação de dados.
- **Entity**: Define os modelos de dados que representam as tabelas no banco de dados.

## Estrutura do Projeto
src/
|-- main/
| |-- java/
| | |-- com/
| | |-- meuprojetocheckout/
| | |-- controller/
| | |-- service/
| | |-- entity/
| | |-- repository/
| |-- resources/
| |-- application.properties

## Dependências

As principais dependências do projeto incluem:

- **Spring Boot Starter Web**: Para construir aplicações web RESTful.
- **Spring Boot Starter Data JPA**: Para integração com bancos de dados usando JPA.
- **MySQL Connector**: Para conectar-se ao banco de dados MySQL.
- **JasperReports**: Para a geração de relatórios.
- **Lombok**: Para reduzir o boilerplate code.

## Configuração do Ambiente com Docker

### Dockerfile

O arquivo `Dockerfile` foi configurado para construir a aplicação em um ambiente Java. Ele inclui as seguintes etapas:

1. Build da aplicação usando Maven.
2. Criação de uma imagem leve a partir do JDK.

### docker-compose.yml

O arquivo `docker-compose.yml` define os serviços necessários para a execução da aplicação:

- **Backend**: O serviço principal que roda a aplicação Spring Boot.
- **Database**: Um serviço MySQL que armazena os dados da aplicação.

#### Exemplo de docker-compose.yml:

```yaml
version: '3.8'
services:
  backend:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/Loja_01
      SPRING_DATASOURCE_USERNAME: AdminLoja01
      SPRING_DATASOURCE_PASSWORD: AdminLoja01
      SPRING_JPA_HIBERNATE_DDL_AUTO: update

  db:
    image: mysql:latest
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: AdminLoja01
      MYSQL_DATABASE: Loja_01
      MYSQL_USER: AdminLoja01
      MYSQL_PASSWORD: AdminLoja01
    ports:
      - "3306:3306"
````
Construa e inicie os serviços com Docker Compose:
docker-compose up --build