-- V1__create_product_policy_tables.sql

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    product_type VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product_coverages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    coverage_code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    coverage_limit DECIMAL(15, 2),
    deductible DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    UNIQUE (product_id, coverage_code)
);

CREATE TABLE policies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_number VARCHAR(100) NOT NULL UNIQUE,
    product_id BIGINT NOT NULL,
    customer_id VARCHAR(36) NOT NULL, -- Assuming UUID for customer_id from another service
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING_PAYMENT',
    premium_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE endorsements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_id BIGINT NOT NULL,
    effective_date DATE NOT NULL,
    description VARCHAR(255),
    change_details TEXT, -- Use TEXT for MySQL, can store JSON string
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (policy_id) REFERENCES policies(id)
);