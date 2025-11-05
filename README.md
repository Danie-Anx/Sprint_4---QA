# 🏍️ Mottu Tracking — Sprint 4 (Compliance, QA & Tests)

> **Objetivo da Sprint:** garantir qualidade do sistema via **plano de testes (AZURE BOARDS)** e **automação de testes (JUnit + RestAssured)**, cobrindo autenticação **JWT** e os endpoints principais de **Motos, Beacons e Localizações**.

---

## 📘 Visão Geral

O **Mottu Tracking** é uma aplicação **Java 21 / Spring Boot 3** com **API REST** para gerenciar **motos**, **beacons Bluetooth** e **localizações**.  
Durante esta sprint, o foco foi em **confiabilidade de endpoints**, **segurança JWT** e **integração Oracle** utilizando **migrations Flyway**.

```
mottooth-java-main/
├── src/
│   ├── main/
│   │   ├── java/br/com/fiap/mottooth/        # Código principal (controllers, services, security, DTOs)
│   │   └── resources/
│   │        ├── application.properties        # Configurações (Oracle, JWT, Flyway, Swagger)
│   │        ├── templates/                    # Páginas Thymeleaf (motos/, beacons/, flows/, fragments/)
│   │        └── db/migration/                 # Scripts Flyway (V1...V13)
│   └── test/
│       └── java/br/com/fiap/mottooth/
│            └── ApiTests.java                 # Testes automatizados (JUnit 5 + RestAssured)
├── pom.xml                                    # Dependências Maven
└── README.md
```

---

## ⚙️ Stack Técnica

- ☕ **Java 21**
- 🚀 **Spring Boot 3.2.3** (Web, Data JPA, Validation, Security, Thymeleaf)
- 🔐 **JWT (jjwt 0.11.5)** – proteção dos endpoints
- 🧠 **Oracle** (ojdbc11)
- 🧩 **Flyway** – versionamento do banco (migrations V1...V13)
- 🧪 **JUnit 5 + RestAssured 5.4.0** – automação de testes
- 📄 **Swagger UI** (`/swagger-ui.html`) – documentação e testes manuais

---

## 🔒 Autenticação (JWT)

### **Endpoint de Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@ex.com",
  "senha": "fiap25"
}
```

### **Cabeçalho de Autorização**
```http
Authorization: Bearer <token>
```

> O token JWT é validado em todas as requisições protegidas da API.

---

## 🧪 Parte B — Testes Automatizados (JUnit + RestAssured)

Arquivo principal:  
`src/test/java/br/com/fiap/mottooth/ApiTests.java`

### ✅ Casos Automatizados

| ID | Endpoint | Descrição | Resultado Esperado |
|----|-----------|------------|--------------------|
| **AUTO-01** | `POST /api/auth/login` | Autentica e retorna JWT | 200 + token |
| **AUTO-02** | `GET /api/motos` | Lista todas as motos | 200 + JSON lista |
| **AUTO-03** | `GET /api/motos/{id}` | Busca moto por ID | 200 ou 404 |
| **AUTO-04** | `GET /api/beacons` | Lista todos os beacons | 200 |
| **AUTO-05** | `GET /api/beacons/{id}` | Busca beacon por ID | 200 ou 404 |
| **AUTO-06** | `GET /api/localizacoes` | Lista todas as localizações | 200 |
| **AUTO-07** | `GET /api/localizacoes/{id}` | Busca localização por ID | 200 ou 404 |

> ⚙️ Os testes realizam o login automaticamente via `@BeforeAll`.  
> Caso o login falhe, os testes protegidos são **pulados** com `assumeTrue` para evitar falsos negativos.

---

## ▶️ Como Executar os Testes

### **Pré-requisitos**
- ☕ Java 21 instalado  
- 🧰 Maven configurado (`mvn -version`)  
- 🗄️ Banco Oracle acessível  
- 🌐 API rodando em `http://localhost:8080`

### **Executar pelo Maven**
```bash
mvn -Dtest=ApiTests test
```

### **Saída esperada**
```
Base URL........: http://localhost:8080
User for login..: joao@ex.com
Token acquired?.: true
[INFO] Tests run: 7, Failures: 0, Skipped: 0
```

### **Executar pela IDE (IntelliJ IDEA)**
1. Abra o arquivo:  
   `src/test/java/br/com/fiap/mottooth/ApiTests.java`
2. Clique com o botão direito e selecione **Run 'ApiTests'**

---

## 🔧 Configuração (application.properties)

Principais parâmetros já configurados:

```properties
# Banco Oracle
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=RM556099
spring.datasource.password=fiap25

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true

# JWT
app.jwt.secret=5K8n1t3yZQfXjLwV9sA2mGhU7cR4oTbE6dP0vNjqYlBzCrFx
app.jwt.expiration=3600000

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

> 💡 Caso precise testar com outro usuário, altere as variáveis de ambiente:
> ```bash
> setx API_USER joao@ex.com
> setx API_PASS fiap25
> ```

---

## 🧾 Evidências (Resultados)

- ✅ **Execução bem-sucedida dos 7 testes automatizados**
- ⚡ **Status retornados:** 200 / 404 conforme esperado
- 🔐 **Login JWT validado com sucesso**
- 💾 **Banco Oracle e Flyway integrados corretamente**
- 📈 **IntelliJ / Maven:** “Tests passed: 7 / 7”

---

## 🗂️ Entregáveis da Sprint 4

| Parte | Descrição | Status |
|-------|------------|--------|
| **A** | Plano de Testes (Azure Boards) | ✅ Concluído |
| **B** | Testes Automatizados (JUnit + RestAssured) | ✅ Concluído |

📎 **Link do Azure Boards:**  
[🔗 Acessar o projeto no Azure DevOps](https://dev.azure.com/RM555881/SPRINT-3%20-%20QA/_workitems/recentlyupdated/)

---

## 👤 Equipe

**RM 555881 — Robert Daniel da Silva Coimbra**
**RM 558798 — Arthur Ramos dos Santos**
**RM 556099 — Felipe Melo de Sousa**


---

## 🏁 Conclusão

A automação de testes com **JUnit 5** e **RestAssured** garantiu a confiabilidade dos endpoints e a segurança da autenticação JWT.  
Com os testes validados e o plano documentado no **Azure Boards**, a entrega da **Sprint 4** demonstra a maturidade técnica do sistema e o foco em **qualidade e boas práticas de QA**.
