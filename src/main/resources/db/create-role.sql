-- liquibase formatted sql

-- changeset chuong.tran:create-role
-- comment Create Role table

CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for the role
    name VARCHAR(50) NOT NULL UNIQUE, -- Role name, e.g., "ROLE_ADMIN", "ROLE_USER"
    description VARCHAR(255), -- Optional: Description of the role
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp when the role was created
);

INSERT INTO role (name, description)
VALUES
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_USER', 'Regular user with limited access'),
('ROLE_MODERATOR', 'User with moderation permissions');

CREATE TABLE user_role (
    user_id BIGINT NOT NULL, -- Foreign key referencing the users table
    role_id BIGINT NOT NULL, -- Foreign key referencing the roles table
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Optional: Timestamp when the role was assigned
    PRIMARY KEY (user_id, role_id), -- Composite primary key to ensure uniqueness
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE, -- Link to users table
    FOREIGN KEY (role_id) REFERENCES role (role_id) ON DELETE CASCADE -- Link to roles table
);