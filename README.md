# Diário RN Reporter API

API REST desenvolvida em Java e Spring Boot para automatizar a geração de relatórios de matérias publicadas por jornalistas.

## Objetivo

O projeto foi desenvolvido para solucionar uma necessidade real do Diário do RN: automatizar a coleta dos links das matérias produzidas por um jornalista durante seu turno.

A aplicação utiliza a API REST do WordPress para identificar o jornalista e buscar as matérias publicadas por ele dentro de um determinado período.

## Funcionalidades atuais

- Integração com a API REST do WordPress
- Busca de jornalistas por username
- Busca de posts por autor
- Busca de posts por intervalo de data e hora
- Conversão das datas para ISO-8601/UTC
- Comunicação HTTP utilizando Spring `RestClient`
- Camada de serviço para orquestração do fluxo
- Validação do período informado
- Tratamento de jornalista não encontrado com exception específica
- Configuração através de variáveis de ambiente
- Testes do cliente HTTP sem acesso ao WordPress real
- Testes unitários da camada de serviço com Mockito

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Maven
- Spring Web MVC
- Spring RestClient
- Bean Validation
- Lombok
- Spring Dotenv
- JUnit
- Mockito
- MockRestServiceServer

## Integração com WordPress

Atualmente, a aplicação utiliza os seguintes recursos da API REST do WordPress:

```http
GET /users?search={username}
```

Utilizado para localizar o usuário correspondente ao jornalista.

```http
GET /posts?author={authorId}&after={begin}&before={end}
```

Utilizado para buscar as matérias publicadas pelo autor dentro do período informado.

Os parâmetros `after` e `before` são convertidos para ISO-8601/UTC antes do envio ao WordPress.

## Camada de serviço

A camada de serviço é responsável por coordenar o fluxo entre as regras de negócio da aplicação e a integração com o WordPress.

Atualmente, o `ReporterService` é responsável por:

- Validar o período informado
- Buscar o jornalista através do `WordpressClient`
- Validar a existência do jornalista
- Recuperar as publicações do jornalista dentro do período informado

Foram criadas exceptions específicas para representar falhas das regras de negócio:

- `JournalistNotFoundException`
- `InvalidPeriodException`

## Testes

O projeto possui testes automatizados para validar a integração HTTP e as regras de negócio da aplicação.

### WordpressClient

Os testes do `WordpressClient` utilizam `MockRestServiceServer`, permitindo testar a comunicação realizada pelo `RestClient` sem acessar o servidor WordPress real.

Atualmente são testados:

- Busca de usuário por username
- Retorno vazio quando o usuário não é encontrado
- Busca de posts por autor e período
- Desserialização das respostas JSON
- Construção das requisições HTTP

### ReporterService

Os testes unitários do `ReporterService` utilizam Mockito para isolar a camada de serviço da integração externa.

Atualmente são testados:

- Busca das publicações de um jornalista
- Jornalista inexistente
- Período inválido
- Interrupção do fluxo quando uma regra de negócio não é atendida

Para executar todos os testes:

```bash
./mvnw test
```

No Windows PowerShell:

```powershell
.\mvnw.cmd test
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
- Conversão das datas para ISO-8601/UTC
- Testes do `WordpressClient`
- Implementação da camada de serviço
- Validação do período
- Exceptions específicas para regras de negócio
- Testes unitários do `ReporterService`

### Próximos passos

- Criar o endpoint REST definitivo
- Criar os DTOs próprios da API
- Implementar validação dos parâmetros de entrada
- Implementar tratamento global de exceptions
- Tratar indisponibilidade da API do WordPress
- Remover o `TestController`
- Gerar o relatório de matérias
- Formatar o relatório para compartilhamento

## Autor

Desenvolvido por Luan Diniz.
