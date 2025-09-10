# End-to-End Insurance Analytics Platform — Medallion Architecture

![Banner](./.assets/banner.png)

**A production-minded, developer-friendly repository template** that captures clickstream from an insurance portal, processes it through a Medallion (Bronze → Silver → Gold) pipeline, and surfaces realtime analytics in a React dashboard.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Key Features](#key-features)
4. [Tech Stack](#tech-stack)
5. [What's Included](#whats-included)
6. [APIs & Data Contracts](#apis--data-contracts)
7. [Data Model & Sample DDL](#data-model--sample-ddl)
8. [ETL: Design & PySpark Plan](#etl-design--pyspark-plan)
9. [Run Locally (Docker Compose)](#run-locally-docker-compose)
10. [Kubernetes / Helm (Local & Cloud)](#kubernetes--helm-local--cloud)
11. [CI / CD & Infrastructure (GitHub Actions, Argo CD, Terraform)](#ci--cd--infrastructure)
12. [Roadmap & Remaining Work](#roadmap--remaining-work)
13. [Acceptance Criteria](#acceptance-criteria)
14. [Security & Operational Notes](#security--operational-notes)
15. [Screenshots & Docs](#screenshots--docs)
16. [License & Contact](#license--contact)

---

## Project Overview

A vertically-integrated implementation of a Medallion Architecture built as a developer prototype. The system captures detailed frontend clickstream (clicks, scrolls, inputs, submissions), ingests events into a Spring Boot `eventcollector` service, persists raw JSON to the **Bronze** layer in MySQL, runs scheduled ETL to the **Silver** layer, and aggregates session-level analytics into the **Gold** layer. A React + Vite dashboard displays Silver & Gold tables in near realtime.

**Primary goals:**

* Provide a clear Bronze→Silver→Gold data flow for clickstream and transactional data
* Ship a reproducible, Dockerized local dev experience
* Provide scaffolding for full microservice + PySpark ETL + GitOps delivery

---

## Architecture

```
[ React Frontend ] --(POST /api/events)--> [ Spring Boot: eventcollector ]
         |                                          |
         |                                          V
         |                                    [ MySQL (Bronze) ]
         |                                          |
         |                                  (Scheduled ETL, every 30s)
         |                                          V
         |                                  [ MySQL (Silver) ]
         |                                          |
         +------------------------------------------+
                    (Analytics API over Silver/Gold)
```

> For quick iteration the ETL runs inside the Spring Boot process as a scheduled job (`Et lService.java`) that runs every **30 seconds**. The long-term target is a PySpark ETL running in Jupyter/Docker that reads/writes MySQL via JDBC.

---

## Key Features

* **Comprehensive Clickstream capture** via a `EventTracker` hook: page views, clicks, inputs, scrolls, hovers, form submits.
* **Event ingestion API**: `POST /api/events` persists raw event JSON to the Bronze layer.
* **Medallion ETL pipeline**:

  * Bronze: `bronze_events` raw JSON (append-only)
  * Silver: `silver_events` typed & cleansed
  * Gold: `gold_user_sessions` session aggregates (duration, pages, conversions)
* **Realtime Dashboard**: React frontend auto-refreshes Silver & Gold tables every **10s**.
* **Dockerized dev stack**: Frontend, backend, MySQL orchestrated via `docker-compose`.

---

## Tech Stack

| Area                    | Technology                                          | Notes                                                    |
| ----------------------- | --------------------------------------------------- | -------------------------------------------------------- |
| Frontend                | React, Vite, TypeScript, Axios                      | EventTracker + Dashboard components                      |
| Backend                 | Java 17, Spring Boot 3.2.5, Maven                   | Single `eventcollector` service (ingest + scheduled ETL) |
| Datastore               | MySQL 8.0                                           | Bronze / Silver / Gold schemas                           |
| ETL                     | Java Scheduled Service (current), PySpark  | PySpark notebooks  (01–04)                        |
| Containers              | Docker, docker-compose                              | Local orchestration for quick dev                        |
| Orchestration  | Helm, Kubernetes (kind/minikube, GKE)               | Helm charts scaffolded                                   |
| CI/CD          | GitHub Actions, Argo CD                             | GitOps delivery                                   |
| IaC            | Terraform (GCP)                                     | GKE, Artifact Registry, Cloud SQL (optional)             |

---

## What's Included

* `frontend/` — React + Vite app, `EventTracker` hook, Analytics dashboard
* `backend/` — Spring Boot eventcollector service (POST /api/events, scheduled ETL)
* `db/` — SQL init scripts and DDL for bronze/silver/gold schemas
* `docker-compose.yml` — quick local setup (frontend, backend, MySQL)
* `helm/` — foundational Helm chart scaffolds
* `etl/` — placeholder for PySpark notebooks and containerized ETL
* `terraform/` — scaffolded GCP Terraform modules (placeholder)
* `docs/` — architecture diagrams, Postman collection stub

---

## APIs & Data Contracts

### Event Ingestion

**POST** `/api/events`

**Description:** Ingest a raw clickstream event (JSON). Stored immediately in `bronze_events`.

**Payload (example):**

```json
{
  "userId": "user-123",
  "sessionId": "sess-456",
  "timestamp": "2025-09-10T09:00:00Z",
  "eventType": "click",
  "page": "/quote/start",
  "elementId": "#continue-btn",
  "meta": { "x": 100, "y": 200, "browser": "Chrome" }
}
```

### Analytics

* **GET** `/api/events/silver` — returns recent Silver (cleansed) events (paginated)
* **GET** `/api/analytics/sessions` — returns Gold-level user session aggregates (filters by date/user)

---

## Data Model & Sample DDL

> These DDLs are intended as a starting point — adjust field sizes and indices for production.

**Bronze (append-only)**

```sql
CREATE TABLE bronze_insurance.clicks_raw (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  ingest_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  payload JSON NOT NULL,
  processed BOOLEAN DEFAULT FALSE,
  src VARCHAR(64)
);
```

**Silver (cleansed)**

```sql
CREATE TABLE silver_insurance.silver_events (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id VARCHAR(128),
  session_id VARCHAR(128),
  event_ts TIMESTAMP,
  event_type VARCHAR(64),
  page VARCHAR(255),
  element_id VARCHAR(255),
  meta JSON,
  raw_id BIGINT,
  INDEX (user_id),
  INDEX (session_id)
);
```

**Gold (session aggregates)**

```sql
CREATE TABLE gold_insurance.gold_user_sessions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  session_id VARCHAR(128),
  user_id VARCHAR(128),
  start_ts TIMESTAMP,
  end_ts TIMESTAMP,
  session_duration_seconds INT,
  pages_visited INT,
  events_count INT,
  converted BOOLEAN DEFAULT FALSE,
  INDEX (user_id)
);
```

---

## ETL: Design & PySpark Plan

**Current:** ETL implemented as a Java `@Scheduled` job inside Spring Boot (`EtlService.java`) that runs every **30 seconds** and:

1. Reads unprocessed rows from `bronze_events`.
2. Parses JSON, validates required fields, normalizes types → writes `silver_events`.
3. Aggregates affected sessions → upserts into `gold_user_sessions`.
4. Marks `bronze_events.processed = true`.


---

## Run Locally (Docker Compose)

### Prerequisites

* Docker Desktop
* Git

### Quick start

```bash
git clone https://github.com/aakanksharajhans8/loki-final-project/tree/new-spartans
cd 'loki-final-project'
# builds images and starts frontend, backend, and mysql
docker-compose up --build
```

* Frontend: [http://localhost:5173](http://localhost:5173)
* Backend API: [http://localhost:8080](http://localhost:8080)
* Health: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

**Tip:** The `docker-compose` file runs an init SQL script (`db_init/init.sql`) to create Bronze/Silver/Gold schemas.

---

## Kubernetes / Helm (Local & Cloud)

* Helm charts are scaffolded under `/helm/apps` (one chart per service)
* Local cluster: kind or minikube + ingress-nginx
* Deploy medallion MySQL with stable/mysql or bitnami/mysql helm chart and mount secrets via Kubernetes Secrets
* Use `values-dev.yaml` for local configs; `values-staging.yaml` for staging

Example:

```bash
# create a kind cluster
kind create cluster
# install ingress
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
# install medallion mysql
helm upgrade --install medallion-mysql ./helm/charts/mysql -f ./helm/charts/mysql/values-dev.yaml
# deploy eventcollector
helm upgrade --install eventcollector ./helm/apps/eventcollector -n dev -f ./helm/apps/eventcollector/values-dev.yaml
```

---

## CI / CD & Infrastructure

**CI:** GitHub Actions

* `build-and-test` — build Java and Node modules, run unit tests
* `docker-build-publish` — build and push images to registry

**CD:** Argo CD watches a GitOps repo containing Helm `values-*.yaml` and syncs to clusters.

**IaC (Terraform):** Scaffold under `/terraform/gcp` to provision:

* VPC, GKE cluster, Artifact Registry, Workload Identity bindings
* Optional Cloud SQL (MySQL) & Secret Manager

Blue/green staging pattern: Two staging namespaces (staging-blue / staging-green) with variable-driven active namespace and Argo CD apps targeting the active namespace; Terraform controls the switch.

---

## Roadmap & Remaining Work

* Move ETL from Java Scheduled job → PySpark notebooks (containerized) for scalable transforms.
* Break monolith into 10+ Spring Boot microservices (Java 21, Spring Boot 3.5.5) with Flyway migrations and per-service DB.
* Implement full CI pipelines that build, test, and push images; wire Argo CD for GitOps deployments.
* Harden security: HTTPS, OAuth2 / JWT, PII masking in Bronze layer.
* Add observability: Prometheus metrics, Grafana dashboards for ingestion/ETL.
* Implement environment-specific configurations (dev/test/staging DB types).

---

## Acceptance Criteria

* Local: frontend + eventcollector + MySQL run via Docker or K8s; clickstream visible in Bronze.
* ETL: Silver & Gold populated and queryable (dashboard shows Silver & Gold tables).
* Analytics: Dashboard implements at least 3 visualizations (e.g., conversions by channel, clicks by page, sessions per user).
* CI/CD: GitHub Actions build images; Argo CD syncs the Helm charts.
* Cloud: Terraform provisions basic infra; GKE runs Helm releases; Cloud SQL accessible if used.

---

## Security & Operational Notes

* **PII:** Review Bronze payloads and mask or encrypt sensitive fields before long-term storage.
* **Backups:** Use DB backups / Cloud SQL automated backups for production data.
* **Secrets:** Use Kubernetes Secrets or Secret Manager; avoid committing credentials.
* **Scaling:** Offload ETL-heavy transforms to PySpark cluster (Databricks / EMR / Spark on K8s) when volume grows.

---

## Screenshots & Docs


* [MySQL Bronze rows](./docs/screenshots/MySQL.png)
* [Silver normalized events](./docs/screenshots/Silver.png)
* [Gold session aggregates](./docs/screenshots/Gold.png)

---

## License

MIT License

---

## Contact

Project owner / Maintainer — [*@vaibhav7k*](https://github.com/vaibhav7k)

