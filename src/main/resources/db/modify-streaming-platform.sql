-- liquibase formatted sql

-- changeset chuong.tran:modify-streaming-platform
-- comment Add url column to streaming_platforms table

ALTER TABLE streaming_platforms
ADD COLUMN url VARCHAR(255);