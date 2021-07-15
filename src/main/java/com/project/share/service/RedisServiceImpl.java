package com.project.share.service;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource(name="redisMessageTemplate")
    private ZSetOperations<String, Object> zSetOperations;

    @Override
    public Set<Object> getRecentMessages(String key) {
        // return redisTemplate.boundZSetOps(key).range(0, -1);
        return zSetOperations.range(key, 0, -1);
    }
}
