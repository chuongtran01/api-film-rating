-- liquibase formatted sql

-- changeset chuong.tran:countries
-- comment Create countries table

CREATE TABLE countries (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    flag            VARCHAR(255) DEFAULT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active          BOOLEAN      DEFAULT TRUE
); 

INSERT INTO countries (id, code, name) VALUES (1, 'vn', 'Vietnam');
INSERT INTO countries (id, code, name) VALUES (2, 'us', 'United States');
INSERT INTO countries (id, code, name) VALUES (3, 'jp', 'Japan');
INSERT INTO countries (id, code, name) VALUES (4, 'kr', 'Korea');
INSERT INTO countries (id, code, name) VALUES (5, 'cn', 'China');
INSERT INTO countries (id, code, name) VALUES (6, 'fr', 'France');
INSERT INTO countries (id, code, name) VALUES (7, 'de', 'Germany');
INSERT INTO countries (id, code, name) VALUES (8, 'es', 'Spain');
INSERT INTO countries (id, code, name) VALUES (9, 'it', 'Italy');