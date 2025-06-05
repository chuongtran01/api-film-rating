-- liquibase formatted sql

-- changeset chuong.tran:languages
-- comment Create languages table

CREATE TABLE languages (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
); 

INSERT INTO languages (id, code, name) VALUES (1, 'en', 'English');
INSERT INTO languages (id, code, name) VALUES (2, 'vi', 'Vietnamese');
INSERT INTO languages (id, code, name) VALUES (3, 'ja', 'Japanese');
INSERT INTO languages (id, code, name) VALUES (4, 'ko', 'Korean');
INSERT INTO languages (id, code, name) VALUES (5, 'zh', 'Chinese');
INSERT INTO languages (id, code, name) VALUES (6, 'fr', 'French');
INSERT INTO languages (id, code, name) VALUES (7, 'de', 'German');
INSERT INTO languages (id, code, name) VALUES (8, 'es', 'Spanish');
INSERT INTO languages (id, code, name) VALUES (9, 'it', 'Italian');