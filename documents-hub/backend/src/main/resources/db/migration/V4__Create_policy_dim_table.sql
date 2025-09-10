CREATE TABLE dim_policy (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_number VARCHAR(100) UNIQUE NOT NULL,
    policy_type VARCHAR(50),
    issued_date DATE,
    expiry_date DATE
);
