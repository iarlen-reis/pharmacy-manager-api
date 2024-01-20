
# Pharmacy Manager API

Pharmacy Manager API é uma aplicação back-end construida com spring para administração de remedios de uma farmácia.


## Funcionalidades
Com essa API é possível gerenciar remédio de foma fácil, A API também conta com autenticação via JWT usando spring security, onde existem 2 tipos de usuário (USER e ADMIN).
## Documentação

A aplicação possui swagger nos controllers, sendo possivel testa-la no swagger, para fazer isso, basta iniciar o projeto e ir na página:

`http://localhost:8080/swagger-ui/index.html/`



## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar adicionar as seguintes variáveis de ambiente no seu `
application.properties
`

Url do banco de dados mysql:

`spring.datasource.url=` 

Nome do usuário do banco:

`spring.datasource.username=`

Senha do usuário do banco:

`spring.datasource.password=`

Desativa o stacktrace:

`server.error.include-stacktrace=never`

Segredo para gerar o token jwt:

`api.security.token.secret=`


## Remédios
Usuário com permissão de ADMIN, podem criar, atualizar, deletar, desabilitar e habilitar um remédio. Ele também pode promover outros usuários a administrador.

Usuários com permissão de USER, podem listar todos os remédios e ver detalhes de um remédio a partir do ID.
## Autenticação
Para acessar qualquer recurso relacionado aos remédios o usuário deve está autenticado, a autenticação da aplicação e feita via JWT. O usuário deve acessar a rota de autenticação `/auth` informando seu `username` e `password` e recebe de volta um `token` para enviar nas requisições.
## Rotas de autenticação e usuário

#### Cria um novo usuário

```http
  POST /users
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `username` | `string` | login do usuário. |
`password` | `string` | senha do usuário.
`role` | `enum` | ADMIN OU USER.

#### Retorna o token para usar nas requisições

```http
  POST /auth
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `username`      | `string` | **Obrigatório**. 
`password` | `string` | **Obrigatório**.

#### Retorna as informações do usuário logado.

```http
  GET /users
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `token do usuário`      | `string` | **Obrigatório**. 

#### Altera a senha do usuário logado.

```http
  PUT /users
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `token do usuário`      | `string` | **Obrigatório**. 
`oldPassword` | `string` | Senha antiga do usuário.
`newPassword` | `string` | Nova senha








## Rotas de remédios

#### Cria um novo remédio

```http
  POST /remedy
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token do usuário`      | `string` | **Obrigatório**. 
| `name` | `string` | Nome do remédio. |
`via` | `enum` | ORAL, RENOSO, NASAL E RETAL.
`batch` | `string` | Lote do remédio.
`amount` | `number` | Quantidade.
`validity` | `string` | validade do remédio (YYYY-DD-MM).
`laboratory` | `enum` | MEDLEY OU ACHE.

#### Retorna todos remédios

```http
  GET /remedy
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `token do usuário`      | `string` | **Obrigatório**. 

#### Retorna um remédio pelo ID.

```http
  GET /remedy/id
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. 
| `token do usuário`      | `string` | **Obrigatório**. 


#### Atualiza um remédio.

```http
  PUT /remedy/id
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `token do usuário`      | `string` | **Obrigatório**. 
| `name` | `string` | Nome do remédio. |
`via` | `enum` | ORAL, RENOSO, NASAL E RETAL.
`batch` | `string` | Lote do remédio.
`amount` | `number` | Quantidade.
`validity` | `string` | validade do remédio (YYYY-DD-MM).
`laboratory` | `enum` | MEDLEY OU ACHE.

### Deleta um remédio pelo ID

```http
  DELETE /remedy/id
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. 
| `token do usuário`      | `string` | **Obrigatório**. 

### Desativa um remédio pelo ID

```http
  DELETE /remedy/disable/id
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. 
| `token do usuário`      | `string` | **Obrigatório**. 

### Ativa um remédio pelo ID

```http
  DELETE /remedy/enable/id
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. 
| `token do usuário`      | `string` | **Obrigatório**. 


## Melhorias

- Adicionar testes unitários.


## Stack utilizada

**Back-end:** Spring, Spring security, JPA, Flaway, Loombok, Java JWT, Swagger.

