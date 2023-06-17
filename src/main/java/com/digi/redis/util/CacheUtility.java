package com.digi.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class CacheUtility {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getConnection() {
        return jedisPool.getResource();
    }

    public void set(String key, String value) {
        Jedis jedis = getConnection();
        jedis.set(key, value);
    }

    public String get(String key) {
        try (Jedis jedis = getConnection()) {
            return jedis.get(key);
        }
    }

    public void delete(String key) {
        try (Jedis jedis = getConnection()) {
            jedis.del(key);
        }
    }

    public void setAndDeleteWithExpire(String key, String value, Integer expire) {
        try (Jedis jedis = getConnection()) {
            jedis.set(key, value);
            if (expire != null) {
                jedis.expire(key, expire);
            }
        }
    }
}
