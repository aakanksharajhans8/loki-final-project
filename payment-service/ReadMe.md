
# Insurance Payment Gateway

A full-stack web application for insurance payments and policy management, built with Spring Boot, React, and MySQL. Containerized with Docker and orchestrated using Docker Compose. Includes CI/CD with GitHub Actions.

---

## Table of Contents

- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
  - [Local Development](#local-development)
  - [Manual Setup](#manual-setup)
- [API Documentation](#api-documentation)
  - [Endpoints](#endpoints)
  - [Models](#models)
- [Features](#features)
- [Deployment](#deployment)
- [CI/CD Pipeline](#cicd-pipeline)
- [Monitoring & Logging](#monitoring--logging)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

---

## Architecture

- **Backend:** Spring Boot (Java 17+), MySQL
- **Frontend:** React (Vite), Axios
- **Database:** MySQL
- **Containerization:** Docker & Docker Compose
- **CI/CD:** GitHub Actions
- **Deployment:** Kubernetes-ready via Helm & ArgoCD (optional)
- **Infrastructure:** Terraform (optional)

---

## Project Structure

├── backend/ # Spring Boot backend
│ ├── src/
│ ├── Dockerfile
│ ├── pom.xml
├── frontend/ # React frontend
│ ├── src/
│ ├── Dockerfile
│ ├── package.json
├── docker-compose.yml # Docker Compose multi-service setup
├── .github/
│ └── workflows/
│ └── ci-cd.yml # GitHub Actions pipeline
└── README.md # Project documentation



---

## Prerequisites

- Java 17+
- Node.js 20+
- Maven 3.6+
- Docker & Docker Compose
- Git

(Optional for K8s/Cloud: kubectl, Helm, Terraform)

---

## Quick Start

### Local Development

1. **Clone the repo**
    ```
    git clone <repository-url>
    cd <project-folder>
    ```

2. **Build the backend JAR (first time only)**
    ```
    cd backend
    ./mvnw clean package -DskipTests
    ```

3. **Launch all services**
    ```
    docker-compose up --build
    ```

4. **Access the application**
    - Frontend: [http://localhost:5173](http://localhost:5173)
    - Backend: [http://localhost:8080](http://localhost:8080)
    - MySQL: localhost:3306

### Manual Setup

1. **Backend**
    ```
    cd backend
    mvn spring-boot:run
    ```

2. **Frontend**
    ```
    cd frontend
    npm install
    npm run dev
    ```

---

## API Documentation

### Endpoints

- `POST /api/payments` – Initiate payment
- `GET /api/policies/:id` – Get policy by ID
- `GET /api/transactions` – List transactions
- `GET /api/transactions/:policyNumber` – Search by policy number

### Models

{
"id": 1,
"policyNumber": "ABC12345",
"customerName": "John Doe",
"email": "john.doe@example.com",
"mobile": "1234567890",
"amount": 3000,
"status": "PAID",
"date": "2025-09-10T09:30:00Z"
}


---

## Features

- ✅ Secure payment workflow
- ✅ Policy and transaction management
- ✅ Transaction search by policy number
- ✅ Responsive UI
- ✅ RESTful API endpoints
- ✅ Persistent MySQL database
- ✅ Dockerized multi-service setup
- ✅ Automated CI/CD pipeline

---

## Deployment

### Local (Docker Compose)

docker-compose up --build


### ArgoCD and Terraform (Optional)

Refer to respective `/argocd` and `/terraform` directories for setup.

---

## CI/CD Pipeline

- Builds backend and frontend images
- Pushes images to Docker Hub
- Runs tests before deploy (Maven & Node.js)
- Automated deployment triggers via GitHub Actions (`.github/workflows/ci-cd.yml`)

---

## Monitoring & Logging

- Spring Boot Actuator endpoints at `/actuator`
- Docker container logs
- (If K8s) use `kubectl logs <pod-name>`

---

## Security

- CORS enabled for frontend-backend
- Input validation (client/server)
- Sensitive config using environment variables

---

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit and push your changes
4. Open a pull request

---




