package com.personal.api_film_rating.service;

import java.time.Duration;

public interface RedisService {
    void save(String key, String value, Duration ttl);

    String get(String key);

    boolean delete(String key);
}
