-- liquibase formatted sql

-- changeset chuong.tran:streaming_platforms
-- comment Create streaming_platforms table

CREATE TABLE streaming_platforms (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    logo            VARCHAR(255) DEFAULT NULL,
    url             VARCHAR(255) DEFAULT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
); 

INSERT INTO streaming_platforms (id, code, name) VALUES (1, 'netflix', 'Netflix');
INSERT INTO streaming_platforms (id, code, name) VALUES (2, 'amazon_prime_video', 'Amazon Prime Video');
INSERT INTO streaming_platforms (id, code, name) VALUES (3, 'hbo_max', 'HBO Max');
INSERT INTO streaming_platforms (id, code, name) VALUES (4, 'disney_plus', 'Disney+');
INSERT INTO streaming_platforms (id, code, name) VALUES (5, 'apple_tv_plus', 'Apple TV+');
INSERT INTO streaming_platforms (id, code, name) VALUES (6, 'hulu', 'Hulu');
INSERT INTO streaming_platforms (id, code, name) VALUES (7, 'peacock', 'Peacock');
INSERT INTO streaming_platforms (id, code, name) VALUES (8, 'paramount_plus', 'Paramount+');
INSERT INTO streaming_platforms (id, code, name) VALUES (9, 'showtime', 'Showtime');