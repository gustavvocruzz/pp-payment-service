````markdown
#Payment Service

Implementação prática do desafio de back-end para uma plataforma de pagamentos simplificada. A aplicação permite o cadastro de usuários comuns e lojistas, além de realizar transferências financeiras entre carteiras/contas.

---

# Tecnologias Utilizadas

- Java 17
- Spring Boot 4
- Spring Data JPA
- Banco de dados H2
- Maven
- RestTemplate
- Lombok

---

# Arquitetura e Estrutura do Projeto

O projeto foi desenvolvido seguindo princípios de separação de responsabilidades e boas práticas de arquitetura.

## Estrutura

### Domain
Contém as entidades principais da aplicação:
- `User`
- `Transaction`

### DTOs
Objetos utilizados para transferência de dados entre as camadas.

### Services
Responsáveis pelas regras de negócio:
- validação de saldo
- autorização de transferência
- notificações
- regras para lojistas

### Repositories
Camada de persistência utilizando `JpaRepository`.

### Controllers
Responsáveis pelos endpoints REST da aplicação.

---

# Regras de Negócio

## Tipos de Usuário
Existem dois tipos de usuários:
- `COMMON`
- `MERCHANT`

## Transferências
- Usuários comuns podem enviar dinheiro.
- Lojistas apenas recebem transferências.

## Validação de Saldo
O sistema impede transferências sem saldo suficiente.

## Autorização Externa
Antes da transferência ser concluída, uma API externa de autorização é consultada.

## Transações
As operações são transacionais:
- qualquer erro gera rollback automático.

## Notificações
Após sucesso da transferência, notificações são enviadas para as partes envolvidas.

---

# Execução do Projeto

## Pré-requisitos

- Java 17
- Maven

````
---

## Entrar na pasta do projeto

```bash
cd PaymentService
```

---

## Executar a aplicação

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw spring-boot:run
```

---

A aplicação ficará disponível em:

```txt
http://localhost:8080
```

---

# Banco H2

Console do H2:

```txt
http://localhost:8080/h2-console
```

## Configurações padrão

```txt
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

---

# Endpoints da API

# Usuários

## Criar Usuário

### Endpoint

```http
POST /users
```

### Payload

```json
{
  "firstName": "Joao",
  "lastName": "Silva",
  "document": "12345678901",
  "email": "joaosilva11@email.com",
  "password": "senhaSegura123",
  "balance": 500.00,
  "userType": "COMMON"
}
```

> Para lojistas, utilize:

```json
"userType": "MERCHANT"
```

---

## Resposta de Sucesso

```json
{
  "id": 1,
  "firstName": "Joao",
  "lastName": "Silva",
  "document": "12345678901",
  "email": "joaosilva11@email.com",
  "balance": 500.00,
  "userType": "COMMON"
}
```

---

# Listar Usuários

## Endpoint

```http
GET /users
```

---

## Resposta

```json
[
  {
    "id": 1,
    "firstName": "Joao",
    "lastName": "Silva",
    "document": "12345678901",
    "email": "joaosilva11@email.com",
    "balance": 500.00,
    "userType": "COMMON"
  },
  {
    "id": 2,
    "firstName": "Loja Tech",
    "lastName": "LTDA",
    "document": "98765432100",
    "email": "contato@lojatech.com",
    "balance": 1000.00,
    "userType": "MERCHANT"
  }
]
```

---

# Transferências

## Criar Transferência

### Endpoint

```http
POST /transactions
```

---

## Payload

```json
{
  "value": 100.00,
  "senderId": 1,
  "receiverId": 2
}
```

---

## Resposta de Sucesso

```json
{
  "id": 52,
  "amount": 100.00,
  "sender": {
    "id": 1,
    "firstName": "Joao",
    "lastName": "Silva",
    "document": "12345678901",
    "email": "joaosilva11@email.com",
    "balance": 400.00,
    "userType": "COMMON"
  },
  "receiver": {
    "id": 2,
    "firstName": "Loja Tech",
    "lastName": "LTDA",
    "document": "98765432100",
    "email": "contato@lojatech.com",
    "balance": 1100.00,
    "userType": "MERCHANT"
  },
  "timeStamp": "2026-05-27T17:23:54.823"
}
```

---

# Possíveis Erros

## Lojista tentando transferir

```json
{
  "message": "Lojistas não podem realizar transferências."
}
```

---

## Saldo insuficiente

```json
{
  "message": "Saldo insuficiente."
}
```

---

## Transação não autorizada

```json
{
  "message": "Transação não autorizada."
}
```

---

# Melhorias Futuras

* Implementação de autenticação com Spring Security + JWT
* Testes unitários
* Dockerização da aplicação
* Mensageria com RabbitMQ/Kafka
* Deploy em cloud


```
```
