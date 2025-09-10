-- Gold Layer: analytics ready
CREATE TABLE IF NOT EXISTS gold_document_stats AS
SELECT DATE(uploaded_at) as upload_day, file_type, status, COUNT(*) as cnt
FROM silver_documents
GROUP BY DATE(uploaded_at), file_type, status;