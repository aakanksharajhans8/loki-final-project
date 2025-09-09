#Customer Microservice – Insurance Platform
 Overview

The Insurance Platform project is built using a microservices architecture.
Each microservice is responsible for a specific domain (10 in total).

This branch contains the Customer Microservice, which handles:

Customer data management (CRUD operations)

KYC (Know Your Customer) flag tracking

Database persistence with MySQL

REST API exposure with Spring Boot

Documentation via Swagger UI

⚙️ Tech Stack

Java 21 + Spring Boot 3.5.5

Hibernate + JPA

MySQL 8.0 (via Docker)

Docker & Docker Compose

Swagger / OpenAPI for API documentation

Frontend (React) for user interface

 Getting Started
1️⃣ Clone the repository
git clone https://github.com/aakanksharajhans8/loki-final-project.git
cd loki-final-project
git checkout customer-service

2️⃣ Run with Docker Compose
docker compose up --build


This will spin up:

MySQL (with customer_db)

Customer Service (Spring Boot app)

3️⃣ Access Services

API: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

Frontend (if running separately): http://localhost:5173
