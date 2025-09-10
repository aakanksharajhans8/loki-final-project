CREATE TABLE fact_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_id BIGINT NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_size BIGINT,
    file_type VARCHAR(50),
    user_id BIGINT,
    FOREIGN KEY (document_id) REFERENCES document_metadata(id)
);
