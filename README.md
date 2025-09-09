
# Insurance Platform - Monorepo (Starter)

This repository is a starter scaffold for the Insurance Platform project.
It includes a runnable **Customer Service** microservice (CRUD + KYC flag), a minimal frontend scaffold,
a placeholder ETL folder (PySpark), Helm charts, CI workflow, Terraform placeholder, and helpful scripts.

## What is included
- `services/customer-service` : Full Spring Boot starter (Java 21) with H2 dev profile, Flyway migration, Dockerfile and Helm chart.
- `services/auth-service` : Placeholder folder for future Auth service.
- `frontend` : Minimal React + Vite scaffold (TypeScript/JS) for the UI and analytics page placeholder.
- `etl` : PySpark notebooks / job placeholders (Bronze/Silver/Gold pipelines are examples).
- `helm` : Helm charts for customer-service (example values and templates).
- `.github/workflows/ci-cd.yaml` : Example CI job to build and push Docker image for the customer-service.
- `docker-compose.mysql.yaml` : Local MySQL compose for local testing.
- `terraform` : Placeholder and README for infra (GKE, Cloud SQL) to be filled later.

## Quick start (Customer Service locally)
1. Unzip and open repo:
   ```bash
   cd insurance-platform/services/customer-service
   mvn -DskipTests package
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```
2. Visit Swagger UI: http://localhost:8080/swagger
3. Create a customer:
   ```bash
   curl -X POST http://localhost:8080/api/customers -H 'Content-Type: application/json' -d '{"firstName":"A","lastName":"B","email":"a.b@example.com"}'
   ```

## Next steps
- Follow the project requirements doc to wire other services, CI/CD, Argo CD, and GKE infra.
- Replace placeholder services with full implementations and unit tests.

---
Generated: starter scaffold to help you proceed step-by-step.
