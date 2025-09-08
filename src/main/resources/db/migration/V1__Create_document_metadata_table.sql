CREATE TABLE document_metadata (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_type VARCHAR(255) NOT NULL,
    reference_id VARCHAR(255) NOT NULL,
    gcs_filename VARCHAR(255) NOT NULL UNIQUE,
    original_filename VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add an index on reference_id and document_type for faster lookups
CREATE INDEX idx_reference_id_doc_type ON document_metadata(reference_id, document_type);
