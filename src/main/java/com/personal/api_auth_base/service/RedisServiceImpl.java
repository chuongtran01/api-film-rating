package com.personal.api_auth_base.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;

    public RedisServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String key, String field, String value, Long expirationTime) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expirationTime != null && expirationTime > 0) {
            redisTemplate.expire(key, expirationTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public String get(String key, String field) {
        // Retrieve a specific field from the hash
        Object value = redisTemplate.opsForHash().get(key, field);

        // Return the value as a string, or null if not found
        return value != null ? value.toString() : null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
