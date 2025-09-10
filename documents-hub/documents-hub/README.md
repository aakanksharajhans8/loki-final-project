# Document Upload & Policy PDF Microservice

This microservice is part of the **Insurance Platform** and is responsible for managing document uploads (policy documents, claim evidence) and generating **policy PDFs** from templates.

It is built with **Java 21 + Spring Boot 3.5.5** and designed to run both locally (Docker/kind/minikube) and in the cloud (GCP GKE with Argo CD).

---

## ✨ Features
- **Document Uploads**
    - `POST /documents` → Upload documents (multipart form-data)
    - File metadata persisted in PostgreSQL/MySQL (filename, size, type, checksum, owner)
    - Deduplication via checksum
    - File size & type restrictions enforced
    - Secure download via **pre-signed URLs**
- **Policy PDF Generation**
    - `POST /policies/{id}/pdf` → Generate PDFs synchronously
    - Async mode: Publishes event to Kafka for background PDF generation
    - Customizable **HTML → PDF templates**
- **Access & Security**
    - JWT-based authentication & authorization
    - Role-based access (Customer, Admin, Underwriter)
- **Events & Integrations**
    - Publishes `document.uploaded` and `policy.pdf.generated` events to Kafka
    - Consumes async requests for batch PDF generation
- **Analytics**
    - Exposes metrics (e.g., daily uploads, PDF generations) via `/actuator` and custom endpoints

---

## 🏗️ Tech Stack
- **Java 21, Spring Boot 3.5.5**
- **Spring Web, Spring Data JPA, Spring Security (JWT)**
- **PostgreSQL/MySQL** (Flyway for DB migrations)
- **Kafka** (async events)
- **OpenAPI/Swagger** (API docs)
- **Docker, Helm, Kubernetes**
- **Terraform (for cloud infra on GCP)**

---

## 📂 Project Structure
```plaintext
loki-final-project/documents-hub
├── src/main/java/com/insurance/document
│   ├── controller/   → REST controllers
│   ├── service/      → Business logic
│   ├── entity/       → JPA entities
│   ├── repository/   → Data access
│   ├── config/       → Security, Kafka, etc.
│   └── DocumentsHubApplication.java
├── src/main/resources
│   ├── application-dev.yaml
│   ├── application-test.yaml
│   ├── db/migration  → Flyway migration scripts
│   └── templates/    → HTML templates for PDFs
├── Dockerfile
├── pom.xml
└── README.md
```


## ⚙️ API Endpoints

| Method | Endpoint                  | Description                       |
|--------|---------------------------|-----------------------------------|
| POST   | `/documents`              | Upload a new document             |
| GET    | `/documents/{id}`         | Get document metadata             |
| GET    | `/documents/{id}/download`| Download via pre-signed URL       |
| POST   | `/policies/{id}/pdf`      | Generate policy PDF (sync/async)  |
| GET    | `/analytics/uploads/daily`| Daily uploads report              |



## 🚀 Run Locally

### Prerequisites
- Java 21
- Maven
- Docker
- PostgreSQL/MySQL running locally (or via Docker Compose)

### Build & Run
```bash
# build the JAR
mvn clean package -DskipTests

# run locally
java -jar target/document-upload-policy-0.0.1-SNAPSHOT.jar
```

### Docker
```bash
# build Docker image
docker build -t document-upload-policy:local .

# run container
docker run -p 8085:8080 document-upload-policy:local
```

## ☁️ Deployment

- **Local:** Helm charts (`helm/apps/document-upload-policy`) for kind/minikube
- **Cloud (GCP):** Terraform provisions GKE + Artifact Registry + Cloud SQL; Argo CD deploys via GitOps

---

## 🧪 Testing

- Unit tests with **JUnit + Mockito**
- Integration tests with **Testcontainers** (PostgreSQL & Kafka)

📖 API docs available at:  
[http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

---

## 👥 Contributors

- **Ashwani** → Upload & Storage
- **Shubham** → PDF Generation
- **Gaurav** → Security & Access
- **Saketh** → Kafka Events & Analytics

---

## 📌 Next Steps

- Implement async PDF queue with Kafka
- Add S3/GCS integration for document storage
- Enhance analytics with custom dashboards  
