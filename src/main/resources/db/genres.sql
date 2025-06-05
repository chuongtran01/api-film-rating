-- liquibase formatted sql

-- changeset chuong.tran:genres
-- comment Create genres table

CREATE TABLE genres (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
); 

INSERT INTO genres (id, code, name) VALUES (1, 'action', 'Action');
INSERT INTO genres (id, code, name) VALUES (2, 'adventure', 'Adventure');
INSERT INTO genres (id, code, name) VALUES (3, 'animation', 'Animation');
INSERT INTO genres (id, code, name) VALUES (4, 'biography', 'Biography');
INSERT INTO genres (id, code, name) VALUES (5, 'comedy', 'Comedy');
INSERT INTO genres (id, code, name) VALUES (6, 'crime', 'Crime');
INSERT INTO genres (id, code, name) VALUES (7, 'documentary', 'Documentary');
INSERT INTO genres (id, code, name) VALUES (8, 'drama', 'Drama');
INSERT INTO genres (id, code, name) VALUES (9, 'family', 'Family');
INSERT INTO genres (id, code, name) VALUES (10, 'fantasy', 'Fantasy');
INSERT INTO genres (id, code, name) VALUES (11, 'history', 'History');
INSERT INTO genres (id, code, name) VALUES (12, 'horror', 'Horror');
INSERT INTO genres (id, code, name) VALUES (13, 'music', 'Music');
INSERT INTO genres (id, code, name) VALUES (14, 'mystery', 'Mystery');
INSERT INTO genres (id, code, name) VALUES (15, 'romance', 'Romance');
INSERT INTO genres (id, code, name) VALUES (16, 'sci-fi', 'Sci-Fi');
INSERT INTO genres (id, code, name) VALUES (17, 'sport', 'Sport');
INSERT INTO genres (id, code, name) VALUES (18, 'thriller', 'Thriller');