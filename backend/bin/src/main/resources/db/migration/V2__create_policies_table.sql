-- This script creates the main 'policies' table for our service.

-- Drop the table if it exists to ensure a clean slate.
DROP TABLE IF EXISTS policies;

CREATE TABLE policies (
    -- A unique identifier for the policy.
    id VARCHAR(36) PRIMARY KEY,

    -- The user_id, a UUID string, linking to the customers table.
    user_id VARCHAR(36) NOT NULL,

    -- Customer details collected from the form.
    customer_name VARCHAR(255) NOT NULL,
    customer_age INT NOT NULL,
    customer_gender VARCHAR(50),
    customer_income BIGINT,
    customer_occupation VARCHAR(255),

    -- Insurance plan details.
    insurance_type VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(100),

    -- Calculated values from our business logic.
    risk_score INT,
    coverage_amount BIGINT NOT NULL,
    monthly_premium BIGINT NOT NULL,
    premium_term_months INT NOT NULL,
    terms_and_conditions TEXT,

    -- Status fields to track the policy through the workflow.
    quote_status VARCHAR(50),
    payment_status VARCHAR(50),

    -- Timestamps.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

