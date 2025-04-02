-- Schema Creation

DROP SCHEMA IF EXISTS loans CASCADE;
CREATE SCHEMA loans;
COMMENT ON SCHEMA loans IS 'Schema for loan management system';

-- Set default schema
SET search_path TO loans;


-- Base Tables (No Dependencies)

-- Mailing Addresses: Stores physical address information
CREATE TABLE IF NOT EXISTS loans.mailing_addresses (
    mailing_addresses_id SERIAL PRIMARY KEY,
    street VARCHAR(45),
    city VARCHAR(45),
    state VARCHAR(45),
    zip VARCHAR(45),
    country VARCHAR(45)
);

-- User Types: Defines user roles (ADMIN, USER, MANAGER)
CREATE TABLE IF NOT EXISTS loans.user_types (
    user_types_id SERIAL PRIMARY KEY,
    user_type VARCHAR(45)
);

-- Application Statuses: Loan application status types
CREATE TABLE IF NOT EXISTS loans.application_statuses (
    application_statuses_id SERIAL PRIMARY KEY,
    application_statuses VARCHAR(10),
    description VARCHAR(100),
    CONSTRAINT unique_status UNIQUE (application_statuses)
);

-- Loan Types: Available types of loans
CREATE TABLE IF NOT EXISTS loans.loan_type (
    loan_type_id SERIAL PRIMARY KEY,
    loan_type VARCHAR(10),
    CONSTRAINT unique_loan_type UNIQUE (loan_type)
);


-- Users and Profiles

-- Users: Core user account information with authentication
CREATE TABLE IF NOT EXISTS loans.users (
    users_id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_types_id INTEGER NOT NULL,
    CONSTRAINT fk_users_user_types
        FOREIGN KEY (user_types_id)
        REFERENCES loans.user_types (user_types_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);


-- Complex Tables (Multiple Dependencies)

-- User Profiles: Extended user information
CREATE TABLE IF NOT EXISTS loans.user_profiles (
    user_profiles_id SERIAL PRIMARY KEY,
    users_id INTEGER ,
    mailing_addresses_id INTEGER ,
    first_name VARCHAR(45) ,
    last_name VARCHAR(45) ,
    phone_number VARCHAR(45),
    credit_score INTEGER ,
    birth_date DATE ,
    CONSTRAINT fk_user_profiles_mailing_addresses
        FOREIGN KEY (mailing_addresses_id)
        REFERENCES loans.mailing_addresses (mailing_addresses_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_user_profiles_users
        FOREIGN KEY (users_id)
        REFERENCES loans.users (users_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT check_credit_score 
        CHECK (credit_score >= 300 AND credit_score <= 850),
    CONSTRAINT check_birth_date 
        CHECK (birth_date <= CURRENT_DATE - INTERVAL '18 years')
);


-- Loan Applications

CREATE TABLE IF NOT EXISTS loans.loan_applications (
    loan_applications_id SERIAL PRIMARY KEY,
    loan_type_id INTEGER,
    application_statuses_id INTEGER,
    user_profiles_id INTEGER,
    principal_balance NUMERIC(10,2),
    interest NUMERIC(5,2),
    term_length INTEGER,
    total_balance NUMERIC(10,2),
    CONSTRAINT fk_loan_applications_loan_type
        FOREIGN KEY (loan_type_id)
        REFERENCES loans.loan_type (loan_type_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_applications_application_statuses
        FOREIGN KEY (application_statuses_id)
        REFERENCES loans.application_statuses (application_statuses_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_applications_user_profiles
        FOREIGN KEY (user_profiles_id)
        REFERENCES loans.user_profiles (user_profiles_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT check_positive_amounts 
        CHECK (principal_balance > 0 AND interest >= 0 AND term_length > 0)
);

-- Indexes for Performance Optimization

-- User related indexes
CREATE INDEX idx_users_username ON loans.users(username);
CREATE INDEX idx_user_profiles_last_name ON loans.user_profiles(last_name);
CREATE INDEX idx_user_profiles_user ON loans.user_profiles(users_id);

-- Loan application related indexes
CREATE INDEX idx_loan_applications_status ON loans.loan_applications(application_statuses_id);
CREATE INDEX idx_loan_applications_type ON loans.loan_applications(loan_type_id);
CREATE INDEX idx_loan_applications_user ON loans.loan_applications(user_profiles_id);

-- Audit Trail Configuration

-- Add cascade delete for user profiles when user is deleted
ALTER TABLE loans.user_profiles
    DROP CONSTRAINT IF EXISTS fk_user_profiles_users,
    ADD CONSTRAINT fk_user_profiles_users
        FOREIGN KEY (users_id)
        REFERENCES loans.users (users_id)
        ON DELETE CASCADE;

COMMIT;


-- Documentation

COMMENT ON TABLE loans.users IS 'Stores user credentials and types';
COMMENT ON TABLE loans.loan_applications IS 'Stores loan application details and status';


-- Verification Queries

-- Schema structure verification
SELECT table_name, column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_schema = 'loans'
ORDER BY table_name, ordinal_position;

-- Constraint verification
SELECT tc.table_schema, tc.table_name, tc.constraint_name, tc.constraint_type
FROM information_schema.table_constraints tc
WHERE tc.table_schema = 'loans';

-- Index verification
SELECT schemaname, tablename, indexname
FROM pg_indexes
WHERE schemaname = 'loans';

-- Verify creation
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_tables 
        WHERE schemaname = 'loans' 
        AND tablename = 'loan_applications'
    ) THEN
        RAISE EXCEPTION 'Database creation failed';
    END IF;
END $$;