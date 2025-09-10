CREATE TABLE IF NOT EXISTS gold_insurance.dim_policy (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    -- Add your columns here, for example:
    policy_number VARCHAR(255) NOT NULL,
    policy_holder VARCHAR(255),
    start_date DATE,
    end_date DATE
    -- ...other columns as needed...
);
