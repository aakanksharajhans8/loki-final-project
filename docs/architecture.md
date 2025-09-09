
Architecture notes (short):
- Frontend (React) -> calls API Gateway -> routes to microservices (Customer, Product, Quotation, etc.)
- Event Collector sends clickstream to Bronze MySQL; ETL (PySpark) transforms Bronze->Silver->Gold.
- Analytics API reads Gold layer (read-only).
- CI builds images; Argo CD/GitOps deploys Helm charts to GKE.
