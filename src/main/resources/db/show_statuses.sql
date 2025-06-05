-- liquibase formatted sql

-- changeset chuong.tran:show_statuses
-- comment Create show_statuses table

CREATE TABLE show_statuses (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
); 

INSERT INTO show_statuses (id, code, name) VALUES (1, 'upcoming', 'Upcoming');
INSERT INTO show_statuses (id, code, name) VALUES (2, 'in_production', 'In Production');
INSERT INTO show_statuses (id, code, name) VALUES (3, 'post_production', 'Post Production');
INSERT INTO show_statuses (id, code, name) VALUES (4, 'released', 'Released');
INSERT INTO show_statuses (id, code, name) VALUES (5, 'cancelled', 'Cancelled');
INSERT INTO show_statuses (id, code, name) VALUES (6, 'on_hold', 'On Hold');
INSERT INTO show_statuses (id, code, name) VALUES (7, 'paused', 'Paused');
INSERT INTO show_statuses (id, code, name) VALUES (8, 'ended', 'Ended');