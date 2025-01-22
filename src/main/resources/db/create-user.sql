-- liquibase formatted sql

-- changeset chuong.tran:create-user
-- comment Create User table

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Primary key for the user table
    user_name VARCHAR(150) NOT NULL UNIQUE, -- Usernames should have a reasonable max length, and UNIQUE ensures no duplicates
    password VARCHAR(255) NOT NULL, -- Store hashed passwords (e.g., bcrypt, with space for its length)
    first_name VARCHAR(100) NOT NULL, -- Limit the length of first name
    last_name VARCHAR(100) NOT NULL, -- Limit the length of last name
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Automatically set the creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Automatically update when the row changes
    active BOOLEAN NOT NULL DEFAULT TRUE, -- Status of the user (active/inactive)
    CONSTRAINT chk_user_active CHECK (active IN (0, 1)) -- Ensure active is always boolean (0 or 1)
);

