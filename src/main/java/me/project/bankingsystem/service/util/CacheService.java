package me.project.bankingsystem.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean canWithdraw(String accId) {
        String key = "withdrawal:" + accId;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        Integer withdrawals = (Integer) operations.get(key);
        if (withdrawals == null) {
            operations.set(key, 1, 1, TimeUnit.MINUTES);
            return true;
        } else if (withdrawals < 3) {
            operations.increment(key);
            return true;
        } else {
            return false;
        }
    }

}
