-- liquibase formatted sql

-- changeset chuong.tran:create-role
-- comment Create Role table

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO roles (name, description)
VALUES
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_USER', 'Regular user with limited access'),
('ROLE_MODERATOR', 'User with moderation permissions');