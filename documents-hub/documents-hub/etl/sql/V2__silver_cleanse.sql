-- Silver Layer: cleaned data
CREATE TABLE IF NOT EXISTS silver_documents AS
SELECT DISTINCT id, filename, file_type, status, uploaded_at
FROM bronze_documents;