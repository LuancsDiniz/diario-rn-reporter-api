# Diário RN Reporter API

API REST desenvolvida em Java e Spring Boot para automatizar a geração de relatórios de matérias publicadas por jornalistas.

## Objetivo

O projeto foi desenvolvido para solucionar uma necessidade real do Diário do RN: automatizar a coleta dos links das matérias produzidas por um jornalista durante seu turno.

A aplicação utiliza a API REST do WordPress para identificar o jornalista e buscar as matérias publicadas por ele dentro de um determinado período.

## Funcionalidades atuais

- Integração com a API REST do WordPress
- Busca de usuários do WordPress
- Busca de posts por autor
- Busca de posts por intervalo de data e hora
- Comunicação HTTP utilizando Spring `RestClient`
- Configuração através de variáveis de ambiente
- Testes do cliente HTTP sem acesso ao WordPress real

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Maven
- Spring Web MVC
- Spring RestClient
- Bean Validation
- Lombok
- Spring Dotenv
- JUnit 5
- MockRestServiceServer

## Integração com WordPress

Atualmente, a aplicação utiliza os seguintes recursos da API REST do WordPress:

```text
GET /users?search={username}
```

Utilizado para localizar o usuário correspondente ao jornalista.

```text
GET /posts?author={authorId}&after={begin}&before={end}
```

Utilizado para buscar as matérias publicadas pelo autor dentro do período informado.

## Testes

Os testes do `WordpressClient` utilizam `MockRestServiceServer`, permitindo testar a comunicação realizada pelo `RestClient` sem acessar o servidor WordPress real.

Atualmente são testados:

- Busca de usuário por username
- Retorno vazio quando o usuário não é encontrado
- Busca de posts por autor e período
- Desserialização das respostas JSON
- Construção das requisições HTTP

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

## Variáveis de ambiente

As configurações locais devem ser definidas em um arquivo `.env`.

Utilize o `.env.example` como referência.

Exemplo:

```env
WORDPRESS_BASE_URL=https://example.com/wp-json/wp/v2
SERVER_PORT=8080
```

O arquivo `.env` não deve ser versionado.

## Status do projeto

🚧 Projeto em desenvolvimento.

### Concluído

- Configuração inicial do projeto
- Configuração das variáveis de ambiente
- Integração com a API REST do WordPress
- Cliente para busca de usuários
- Cliente para busca de posts
- Testes do `WordpressClient`

### Próximos passos

- Implementação da camada de serviço
- Regras de negócio e validações
- Endpoint REST definitivo
- Tratamento de erros
- Geração do relatório de matérias
- Formatação do relatório para compartilhamento

## Autor

Desenvolvido por Luan Diniz.
