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

| Method | Endpoint                    | Description                      |
|--------|-----------------------------|----------------------------------|
| GET    | `/documents/user/{user_id}` | Get all documents by user ID     |
| POST   | `/documents/upload`         | Upload a new document            |
| POST   | `/documents/download`       | Download via pre-signed URL      |
| POST   | `/documents/policies/{id}/pdf`        | Generate policy PDF (sync/async) |
| POST   | `/documents/claims/{id}/pdf`          | Generate Claims PDF (sync/async) |



## 🚀 Run Locally

### Prerequisites
- Java 21
- Maven
- Docker
- MySQL running locally (or via Docker Compose)

### Build & Run
```bash
# build the JAR
mvn clean package -DskipTests

# run locally
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Docker
### 1. Build Docker image
```bash
docker build -t my-backend-image .
````

### 2. Run MySQL container (if not already running):
```bash
docker run -d \
  --name my-sql \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_DATABASE=documents_hub_db \
  mysql:8
```

### 3. Run the backend container:
```bash
docker run -d \
  --name backend-app \
  -p 8080:8080 \
  --network my-network \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://my-sql:3306/documents_hub_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  my-backend-image
```

## ☁️ Deployment

- **Local:** Helm charts (`helm/apps/document-upload-policy`) for kind/minikube
- **Cloud (GCP):** Terraform provisions GKE + Artifact Registry + Cloud SQL; Argo CD deploys via GitOps

---

## 🧪 Testing

- Unit tests with **JUnit + Mockito**
- Integration tests with **Testcontainers** (MySQL)

📖 API docs available at:  
[http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

---

## Docker Images
1. Backend
```bash
docker pull mohindar/documents-hub-backend
```

---
## 👥 Contributors

- **Ashwani Kumar** → Upload & Storage on GCS
- **Shubham Chandrasekhar Acharekar** → PDF Generation
- **Gaurav Melkani** → Security & Access
- **Mohindar Saketh** → Events & Analytics

---
