package com.personal.api_auth_base.service;

public interface RedisService {
    void save(String key, String field, String value, Long expirationTime);

    String get(String key, String field);

    void delete(String key);
}
