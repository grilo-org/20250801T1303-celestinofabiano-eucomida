# euComida - Backend

## 📌 Visão Geral
O `euComida` é um backend projetado para suportar um marketplace de delivery de comida. O sistema foi desenvolvido com foco em escalabilidade, segurança e boas práticas de desenvolvimento. Esta documentação detalha a arquitetura atual, tecnologias utilizadas e estratégias adotadas para autenticação e segurança.

---

## 🏗 Arquitetura do Projeto

### 🔹 Tecnologias e Frameworks Utilizados
- **Linguagem**: Java 21
- **Framework Principal**: Spring Boot 3.4.3
- **Persistência**: Spring Data JPA + Hibernate
- **Banco de Dados**: PostgreSQL
- **Autenticação e Segurança**: OAuth2 (Google), JWT, Spring Security
- **Testes**: JUnit 5, AssertJ, Mockito
- **Documentação da API**: OpenAPI (Swagger)
- **Gerenciamento de Dependências**: Maven
- **Migrações de Banco**: Flyway
- **Containerização**: Docker + Docker Compose
- **Gerenciamento de Conexões**: HikariCP

---

## 🛢 Estrutura do Banco de Dados

O sistema utiliza um banco **relacional (PostgreSQL)** com um modelo normalizado para melhor integridade dos dados.

### 📊 Principais Tabelas:
1. **Usuários (`users`)**
   - `id` (UUID) - Identificador único  
   - `name` (String) - Nome do usuário  
   - `email` (String) - E-mail (único)  
   - `password` (String) - Hash da senha (para usuários sem OAuth2)  
   - `created_at` (LocalDateTime) - Data de criação  
   - `updated_at` (LocalDateTime) - Última atualização  

2. **Entregadores (`couriers`)**  
   - `id` (UUID) - Identificador único  
   - `user_id` (UUID) - Relacionamento com a tabela `users`  
   - `vehicle_type` (ENUM) - Tipo de veículo (`BIKE`, `CAR`, `MOTO`)  

3. **Pedidos (`orders`)**  
   - `id` (UUID) - Identificador único  
   - `user_id` (UUID) - Relacionamento com a tabela `users`  
   - `courier_id` (UUID) - Relacionamento com `couriers`  
   - `status` (ENUM) - Status (`PENDING`, `IN_PROGRESS`, `DELIVERED`, `CANCELED`)  
   - `payment_status` (ENUM) - Status do pagamento (`PENDING`, `PAID`, `FAILED`)  
   - `created_at` (LocalDateTime) - Data de criação  

O controle de versões do banco é gerenciado pelo **Flyway**.

---

## 🔑 Estratégia de Autenticação e Autorização

A segurança do sistema é baseada em **OAuth2 e JWT**:
- O backend delega a autenticação ao Google via OAuth2.
- O sistema gera **tokens JWT** para sessões autenticadas.
- O **Spring Security** gerencia permissões e acessos com base no tipo de usuário.
- Tokens possuem **tempo de expiração** e necessitam de renovação periódica.

---

## 🚀 Estratégia de Escalabilidade e Segurança da API

### 🔹 Escalabilidade:
- Estruturado para suportar **múltiplas instâncias** (horizontal scaling futuro).
- Uso de **HikariCP** para otimização de conexões ao banco.

### 🔹 Segurança:
- **Autenticação via OAuth2 e JWT** para evitar acessos não autorizados.
- **Rate Limiting** via Spring Boot (proteção contra ataques DDoS).
- **Criptografia de senhas** usando BCrypt.
- **CORS configurado** para restringir acessos indesejados.

---

## 🛠 Como Rodar o Projeto

### 🔹 Pré-requisitos:
1. **Docker e Docker Compose** instalados.
2. **Java 21 e Maven** instalados.

### 🔹 Passos para rodar:
```sh
# Clonar o repositório
git clone https://github.com/seu-repositorio/eucomida-backend.git
cd eucomida-backend

# Construir o projeto
mvn clean install

# Subir os containers (PostgreSQL + API)
docker-compose up -d

# A API estará rodando em http://localhost:8098
```

### 🔹 Executar testes:
```sh
mvn test
```

---

## 📚 Próximos Passos

- Implementação de **microserviços** e **API Gateway**.
- Adicionar **WebSockets** para rastreamento de pedidos em tempo real.
- Monitoramento com **Prometheus e Grafana**.
- Implementação de **caching com Redis** para otimizar desempenho.

---

Essa documentação reflete o estado atual do projeto. Caso algo precise ser ajustado, me avise! 🚀

