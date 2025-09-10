CREATE OR REPLACE VIEW daily_document_stats AS
SELECT
    DATE(f.upload_time) AS day,
    f.file_type,
    m.status,
    COUNT(*) AS total_count
FROM fact_documents f
JOIN document_metadata m ON f.document_id = m.id
GROUP BY DATE(f.upload_time), f.file_type, m.status;
