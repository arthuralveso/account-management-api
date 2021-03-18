# Account Management api

## API de gerenciamento de contas e transações desenvolvida como desafio do LTI da UNIFACISA

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Java (jdk 8+)](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html), [Maven](http://maven.apache.org/).


Além disto você é necessário o [PostgreSQL](https://www.postgresql.org/).

### Rodando o Back End (servidor)

```bash
# Clone este repositório
$ git clone <https://github.com/arthuralveso/account-management-api>

# Acesse a pasta do projeto no terminal/cmd
$ cd account-management-api

# Instale as dependências
$ mvn clean install

# Execute a aplicação em modo de desenvolvimento
$ mvn spring-boot:run

# O servidor inciará na porta:8080 - acesse <http://localhost:8080>
```
Ou no eclipse siga File > import > maven > existing maven projects. e selecione a pasta do projeto que você clonou. Apos isso vá na classe principal > botão direito > Run as > Java Project

### Banco de dados

No caminho main/resources/application.properties você deve alterar as informações para conectar com o seu banco local

### Documentação

 Ao rodar o projeto você poderá acessar <http://localhost:8080/swagger-ui.html> para ter acesso aos end-points e documentação da API

### Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Java](https://www.oracle.com/br/java)
- [Maven](http://maven.apache.org/)
- [Lombok](https://projectlombok.org/)
- [Swagger (dependencia)](https://swagger.io/)
