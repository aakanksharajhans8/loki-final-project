# 📩 Notification Service  

## 📖 Overview  
The **Notification Service** is a microservices-based application designed to handle user notifications efficiently across different channels. It follows a modular architecture with separate components for backend, frontend, ETL processing, Helm-based deployment, and GitOps integration with ArgoCD.  

This project demonstrates **end-to-end containerized microservice deployment** using **Docker, Helm, Kubernetes, and ArgoCD** while ensuring scalability, automation, and maintainability.  

---

## 🏗️ Repository Structure  

```
notification-service/
├── argocd/                # GitOps configuration for managing deployments via ArgoCD
├── backend/               # Spring Boot microservice handling notification APIs
├── etl/                   # ETL (Extract, Transform, Load) jobs for processing data
├── frontend/              # React-based UI for user interaction
├── helm/Backend/          # Helm charts for deploying backend microservice
├── notification-service/  # Core service logic and configurations
├── .env                   # Environment variables for Docker and services
├── docker-compose.yaml    # Docker Compose file for local setup
├── howToTestBackend       # Guide to test backend APIs
└── README.md              # Project documentation
```

---

## ⚙️ Components  

### 1. **Backend (`/backend`)**  
- Built using **Spring Boot**.  
- Exposes REST APIs for notification management.  
- Connects with external services (email, SMS, etc.) via configuration in `.env`.  
- Packaged with **Dockerfile** and deployable using **Helm charts**.  

### 2. **Frontend (`/frontend`)**  
- Developed with **React.js**.  
- Provides UI for sending and tracking notifications.  
- Integrated with backend REST APIs.  

### 3. **ETL (`/etl`)**  
- Handles **data pipeline** tasks: extracting raw logs, transforming them into structured form, and loading into databases for analysis.  
- Ensures clean and analytics-ready data.  

### 4. **Helm (`/helm/Backend`)**  
- Contains **Helm charts** for Kubernetes deployment of the backend.  
- Supports custom values for environment-specific configurations.  
- Provides seamless scaling and rolling updates.  

### 5. **ArgoCD (`/argocd`)**  
- Enables **GitOps**-style CI/CD pipeline.  
- Automatically syncs changes from GitHub repo to Kubernetes cluster.  
- Ensures **declarative infrastructure** with version control.  

---

## 📦 Deployment  

### 🔹 Local Development (Docker Compose)  
1. Clone the repository:  
   ```bash
   git clone https://github.com/Shreeyamohanty6/notification-service.git
   cd notification-service
   ```
2. Configure `.env` file with required variables.  
3. Run containers:  
   ```bash
   docker-compose up --build
   ```
4. Access services:  
   - Backend API → `http://localhost:8080`  
   - Frontend UI → `http://localhost:3000`  

---

### 🔹 Kubernetes Deployment (Helm + ArgoCD)  

1. Package Helm chart:  
   ```bash
   helm package helm/Backend
   ```
2. Install to cluster:  
   ```bash
   helm install notification-backend ./backend-<version>.tgz
   ```
3. For GitOps:  
   - Push Helm manifests to repo.  
   - Configure ArgoCD to track `argocd/` directory.  
   - ArgoCD auto-syncs deployment in cluster.  

---

## 🔄 ETL Workflow  

1. **Extract** → Collect raw notification events/logs.  
2. **Transform** → Clean, normalize, and enrich data.  
3. **Load** → Store in database/warehouse for analytics and monitoring.  

This ensures proper tracking of notifications, failures, and performance.  

---

## 🧪 Testing  

- Use **Postman** or **cURL** for backend API testing.  
- Follow `howToTestBackend` file for step-by-step instructions.  
- Example:  
   ```bash
   curl -X POST http://localhost:8080/api/notifications      -H "Content-Type: application/json"      -d '{"email":"test@example.com","message":"Hello World"}'
   ```

---

## 🚀 Tech Stack  

- **Backend:** Spring Boot, Java  
- **Frontend:** React.js  
- **Containerization:** Docker, Docker Compose  
- **Orchestration:** Kubernetes  
- **Deployment:** Helm, ArgoCD (GitOps)  
- **Data Processing:** ETL pipelines  

---

## 📌 Future Enhancements  

- Multi-channel notifications (SMS, Push).  
- Centralized logging with ELK/Prometheus.  
- Kafka-based event-driven notifications.  
- CI/CD integration with GitHub Actions + ArgoCD.  
