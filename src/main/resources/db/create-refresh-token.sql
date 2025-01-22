-- liquibase formatted sql

-- changeset chuong.tran:create-refresh-token
-- comment Create Refresh token table

CREATE TABLE refresh_token (
    refresh_token_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for the record
    refresh_token VARCHAR(255) NOT NULL UNIQUE, -- The refresh token string
    user_id BIGINT NOT NULL, -- Foreign key to associate with the user
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the token was created
    expires_at TIMESTAMP NOT NULL, -- Expiration time for the token
    revoked BOOLEAN DEFAULT FALSE, -- Flag to indicate if the token has been revoked
    ip_address VARCHAR(45), -- Optional: IP address of the user for additional security
    user_agent TEXT, -- Optional: User agent details for context
    FOREIGN KEY (user_id) REFERENCES user (user_id) -- Foreign key constraint linking to the user table
);