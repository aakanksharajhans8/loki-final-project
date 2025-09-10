CREATE TABLE underwriting_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    condition TEXT NOT NULL,
    weight INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE underwriting_decisions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_id VARCHAR(64) NOT NULL,
    decision VARCHAR(32) NOT NULL,
    score INT NOT NULL,
    details TEXT,
    evaluated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
