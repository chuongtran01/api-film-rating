-- liquibase formatted sql

-- changeset chuong.tran:create-user
-- comment Create User table    

CREATE TABLE users (
    id              BINARY(16)   NOT NULL PRIMARY KEY,
    display_name    VARCHAR(100) DEFAULT NULL UNIQUE,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    dob             DATE         DEFAULT NULL,
    role_id         INT          DEFAULT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active          BOOLEAN      DEFAULT TRUE
);
