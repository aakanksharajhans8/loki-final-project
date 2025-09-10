-- Bronze Layer: raw data ingestion
CREATE TABLE IF NOT EXISTS bronze_documents AS
SELECT * FROM staging.documents;