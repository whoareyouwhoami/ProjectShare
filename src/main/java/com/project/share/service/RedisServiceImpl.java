package com.project.share.service;

import com.project.share.model.ChatMessage;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource(name="redisTemplate")
    private ZSetOperations<String, ChatMessage> zSetOperations;

    @Override
    public Set<ChatMessage> getMessageList(String key) {
        return zSetOperations.range(key, 0, -1);
    }

    @Override
    public void saveMessage(ChatMessage message) {
        String key = "message:" + message.getRoomNumber();
        Timestamp sentTime = Timestamp.valueOf(message.getSentTime());
        zSetOperations.add(key, message, sentTime.getTime());
    }
}
