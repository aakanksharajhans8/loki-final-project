# 🛡️ Underwriting Microservice

## 📌 Overview

The **Underwriting Service** is a core component of the insurance platform. It evaluates insurance applications by applying **business rules and risk models** to determine whether a policy can be issued and under what terms (premium, coverage, exclusions).

This service integrates with other microservices such as **Customer, Product & Policy, Quotation, and Payments**, and contributes risk scoring data to the **Analytics API**.

---

## ⚙️ Key Responsibilities

* Apply **underwriting rules and scoring logic** on incoming quotations.
* Fetch required **customer, product, and policy** details from other services.
* Return an **underwriting decision** (approve, reject, refer, request additional info).
* Generate and persist **risk scores** for further analysis.
* Expose REST APIs with **OpenAPI/Swagger documentation**.
* Support multi-environment configuration (dev/test/staging).

---

## 🏗️ Architecture

* **Framework:** Spring Boot 3.5.5 (Java 21)
* **Build Tool:** Maven/Gradle
* **Database:**

  * Dev → H2 (in-memory)
  * Test → MySQL
  * Staging → PostgreSQL
* **Migration:** Flyway for schema evolution
* **Security:** JWT validation (via Auth Service)
* **Containerization:** Docker & Helm for deployment
* **CI/CD:** GitHub Actions → Argo CD (GitOps)
* **Infrastructure:** Terraform + GKE (Google Kubernetes Engine)

---

## 📂 Project Structure

```
/services/underwriting-service
 ├── src/main/java/com/insurance/underwriting
 │    ├── controller/       # REST endpoints
 │    ├── service/          # Business logic & rules engine
 │    ├── repository/       # JPA repositories
 │    ├── model/            # Entities & DTOs
 │    └── config/           # Security & DB configs
 ├── src/main/resources
 │    ├── application-dev.yaml
 │    ├── application-test.yaml
 │    ├── application-staging.yaml
 │    └── db/migration/     # Flyway migration scripts
 ├── Dockerfile             # Multi-stage build
 ├── pom.xml                # Dependencies & build config
 └── README.md              # Documentation
```

---

## 🚀 Running Locally

### Prerequisites

* Java 21
* Maven/Gradle
* Docker Desktop
* MySQL/PostgreSQL (via Docker or local)

### Steps

1. Clone the repo and navigate to the underwriting service:

   ```bash
   git clone https://github.com/<org>/insurance-platform.git
   cd services/underwriting-service
   ```
2. Build the application:

   ```bash
   mvn clean package -DskipTests
   ```
3. Run locally (dev profile with H2):

   ```bash
   java -jar target/underwriting-service.jar --spring.profiles.active=dev
   ```
4. Verify endpoints:

   * Health Check → `http://localhost:8080/actuator/health`
   * API Docs → `http://localhost:8080/swagger-ui.html`

---

## 🐳 Docker & Kubernetes

### Build & Run with Docker

```bash
docker build -t underwriting-service:local .
docker run -p 8080:8080 underwriting-service:local
```

### Deploy to Local K8s with Helm

```bash
helm upgrade --install underwriting ./helm/apps/underwriting-service \
  -n dev -f ./helm/apps/underwriting-service/values-dev.yaml
```

---

## 🔑 APIs (Examples)

| Method | Endpoint                 | Description                            |
| ------ | ------------------------ | -------------------------------------- |
| POST   | `/underwriting/evaluate` | Evaluate a quotation & return decision |
| GET    | `/underwriting/{id}`     | Fetch underwriting result by ID        |
| GET    | `/actuator/health`       | Health check endpoint                  |

---

## 📊 Integration with ETL & Analytics

* All underwriting decisions are logged into **transactional DB tables**.
* Events are ingested into **Bronze Layer** via PySpark ETL.
* Processed into **Silver & Gold layers** for reporting (e.g., risk score distributions, approval rates).
* Analytics API fetches aggregated results from **Gold schema**.

---

## 🔒 Security

* JWT tokens issued by the **Auth Service** are validated.
* Role-based access (e.g., `UNDERWRITER`, `ADMIN`).
* Sensitive configs stored in **Kubernetes Secrets** or env vars.

---

## 🧪 Testing

* **Unit Tests:** JUnit 5 + Mockito
* **Integration Tests:** Testcontainers (MySQL/Postgres)
* **Contract Tests:** Spring Cloud Contract for API compatibility

Run tests:

```bash
mvn test
```

---

## 📈 CI/CD Pipeline

1. GitHub Actions builds & tests code.
2. Docker image is pushed to registry.
3. Argo CD syncs Helm chart updates to cluster.
4. Promotion between environments via GitOps workflow.

---

## ✅ Acceptance Criteria

* Service can evaluate underwriting rules & return a decision.
* Integrated with Customer, Product, and Quotation services.
* End-to-end flow: **quote → underwriting → policy issue → payment → claim**.
* Underwriting results appear in **ETL → Gold Layer → Analytics Dashboard**.
