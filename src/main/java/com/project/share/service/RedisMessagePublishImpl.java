package com.project.share.service;

import com.project.share.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RedisMessagePublishImpl implements RedisMessagePublish {
    @Autowired
    private RedisTemplate<String, ChatMessage> redisTemplate;

    @Override
    public void publish(ChannelTopic topic, ChatMessage message) {
        // Save chat in Redis
        String key = "message:" + message.getRoomNumber();
        Timestamp sentTime = Timestamp.valueOf(message.getSentTime());
        redisTemplate.boundZSetOps(key).add(message, sentTime.getTime());

        // Push to Redis pub/sub
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
