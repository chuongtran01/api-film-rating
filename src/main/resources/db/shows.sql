-- liquibase formatted sql

-- changeset chuong.tran:shows
-- comment Create shows table and its relationship tables

CREATE TABLE shows (
    id                  BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    description         TEXT,
    release_date        DATE,
    show_type_id        INT  NOT NULL,  -- Foreign key to show_types
    duration            INT,                    -- in minutes
    poster              VARCHAR(255),           -- URL to poster image
    trailer             VARCHAR(255),           -- URL to trailer video
    rating              DECIMAL(3,1),           -- Average rating (0.0 to 10.0)
    status_id           INT  NOT NULL,  -- Foreign key to show_statuses
    language_id         INT  NOT NULL,  -- Foreign key to languages
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (status_id) REFERENCES show_statuses(id),
    FOREIGN KEY (language_id) REFERENCES languages(id),
    FOREIGN KEY (show_type_id) REFERENCES show_types(id)
);

-- Many-to-many relationship between shows and genres
CREATE TABLE show_genres (
    show_id             BIGINT       NOT NULL,
    genre_id            INT  NOT NULL,
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (show_id, genre_id),
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- Many-to-many relationship between shows and streaming platforms
CREATE TABLE show_streaming_platforms (
    show_id             BIGINT       NOT NULL,
    platform_id         INT  NOT NULL,
    url                 VARCHAR(255) NOT NULL,  -- URL to watch the show on this platform
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (show_id, platform_id),
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES streaming_platforms(id) ON DELETE CASCADE
);

-- Many-to-many relationship between shows and countries
CREATE TABLE show_countries (
    show_id             BIGINT       NOT NULL,
    country_id          INT  NOT NULL,
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (show_id, country_id),
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE,
    FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
); 