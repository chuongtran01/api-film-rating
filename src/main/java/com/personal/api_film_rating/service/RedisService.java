package com.personal.api_film_rating.service;

public interface RedisService {
    void save(String key, String field, String value, Long expirationTime);

    String get(String key, String field);

    void delete(String key);
}
