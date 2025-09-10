-- Add the user_id column to the document_metadata table
ALTER TABLE document_metadata
ADD COLUMN user_id VARCHAR(255) NOT NULL;

-- Add an index on user_id for faster lookups of a user's documents
CREATE INDEX idx_user_id ON document_metadata(user_id);
