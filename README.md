# 🏍️ Mottu Tracking — Sprint 4 (Compliance, QA & Tests)

> **Objetivo da Sprint:** garantir qualidade do sistema via **plano de testes (AZURE BOARDS)** e **automação de testes (JUnit + RestAssured)**, cobrindo autenticação **JWT** e os endpoints principais de **Motos, Beacons e Localizações**.

---

## 📘 Visão Geral

O **Mottu Tracking** é uma aplicação **Java 21 / Spring Boot 3** com API REST para gerenciar **motos**, **beacons Bluetooth** e **localizações**.  
Nesta sprint focamos em: **confiabilidade de endpoints**, **segurança JWT** e **integração Oracle** com **migrations Flyway**.

mottooth-java-main/
├── src/
│ ├── main/
│ │ ├── java/br/com/fiap/mottooth/ # Código principal (controllers, services, security, DTOs)
│ │ └── resources/
│ │ ├── application.properties # Oracle, JWT, Flyway, Swagger
│ │ ├── templates/ # Thymeleaf (motos/, beacons/, flows/, fragments/)
│ │ └── db/migration/ # Migrations Flyway (V1...V13)
│ └── test/
│ └── java/br/com/fiap/mottooth/
│ └── ApiTests.java # Testes automatizados (JUnit 5 + RestAssured)
├── pom.xml # Dependências Maven
└── README.md


---

## ⚙️ Stack Técnica

- **Java 21**, **Maven**
- **Spring Boot 3.2.3** (Web, Data JPA, Validation, Security, Thymeleaf)
- **JWT (jjwt 0.11.5)** para proteção dos endpoints
- **Oracle** (ojdbc11), **Flyway** (migrations V1…V13)
- **JUnit 5** + **RestAssured 5.4.0** (testes de API)
- **Swagger UI** (`/swagger-ui.html`) para inspeção manual

---

## 🔒 Autenticação (JWT)

**Endpoint de login**

POST /api/auth/login
Content-Type: application/json

{ "email": "joao@ex.com
", "senha": "fiap25" }


---

## 🧪 Parte B — Testes Automatizados (JUnit + RestAssured)

Arquivo principal: `src/test/java/br/com/fiap/mottooth/ApiTests.java`

### ✅ Casos Automatizados

| ID       | Endpoint                     | Descrição                                  | Esperado                     |
|----------|------------------------------|---------------------------------------------|------------------------------|
| AUTO-01  | `POST /api/auth/login`       | Autentica e retorna JWT                     | `200` + token                |
| AUTO-02  | `GET  /api/motos`            | Lista todas as motos                        | `200` + JSON lista           |
| AUTO-03  | `GET  /api/motos/{id}`       | Busca moto por ID                           | `200` ou `404`               |
| AUTO-04  | `GET  /api/beacons`          | Lista todos os beacons                      | `200`                        |
| AUTO-05  | `GET  /api/beacons/{id}`     | Busca beacon por ID                         | `200` ou `404`               |
| AUTO-06  | `GET  /api/localizacoes`     | Lista todas as localizações                 | `200`                        |
| AUTO-07  | `GET  /api/localizacoes/{id}`| Busca localização por ID                    | `200` ou `404`               |

> Observação: Os testes tentam logar no `@BeforeAll`. Se o login falhar, os testes protegidos são **pulados** (Assumptions) para evitar falso negativo da suíte.

---

## ▶️ Como Executar os Testes

### Pré-requisitos
- **Java 21** instalado (`java -version`)
- **Maven** instalado (`mvn -version`)
- **Oracle** acessível conforme `application.properties`
- API em execução em **http://localhost:8080**

### Rodando pelo Maven
```bash
mvn -Dtest=ApiTests test

---

🔧 Configuração (application.properties)

Pontos relevantes já configurados:

Oracle (spring.datasource.*)

Flyway (baseline, schema, validação)

JWT (app.jwt.secret e app.jwt.expiration)

Swagger (/swagger-ui.html)

Dica: caso precise alterar credenciais de login usadas nos testes, ajuste as variáveis de ambiente API_USER e API_PASS ou edite os defaults no ApiTests.

Exemplo de execução com variáveis:

Base URL........: http://localhost:8080
User for login..: joao@ex.com
Token acquired?.: true
[INFO] Tests run: 7, Failures: 0, Skipped: 0v

🧾 Evidências (Resultados)

Execução da suíte: “Tests passed: 7 / 7” (IntelliJ / Maven)

Login JWT validado, rotas protegidas respondendo 200; rotas por ID validadas com 200/404 conforme existência dos registros.

👤 Autor

RM 555881 — Robert Daniel da Silva Coimbra
