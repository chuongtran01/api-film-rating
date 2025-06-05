-- liquibase formatted sql

-- changeset chuong.tran:show-type
-- comment Add type column to show table

CREATE TABLE show_types (
    id                  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code                VARCHAR(100) NOT NULL UNIQUE,
    name                VARCHAR(255) NOT NULL,
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO show_types (id, code, name) VALUES (1, 'movie', 'Movie');
INSERT INTO show_types (id, code, name) VALUES (2, 'tv_show', 'TV Show');
INSERT INTO show_types (id, code, name) VALUES (3, 'anime', 'Anime');
INSERT INTO show_types (id, code, name) VALUES (4, 'documentary', 'Documentary');
INSERT INTO show_types (id, code, name) VALUES (5, 'short_film', 'Short Film');
INSERT INTO show_types (id, code, name) VALUES (6, 'web_series', 'Web Series');
INSERT INTO show_types (id, code, name) VALUES (7, 'trailer', 'Trailer');

