# File: db_init/init.sql
CREATE DATABASE IF NOT EXISTS medallion_db;
USE medallion_db;

CREATE TABLE IF NOT EXISTS bronze_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payload TEXT,
    received_at TIMESTAMP NOT NULL,
    processed BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS silver_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    session_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    page VARCHAR(255) NOT NULL,
    element_id VARCHAR(255),
    event_timestamp TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS gold_user_sessions (
    session_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    session_start TIMESTAMP NOT NULL,
    session_end TIMESTAMP,
    event_count BIGINT,
    converted BOOLEAN DEFAULT FALSE
);