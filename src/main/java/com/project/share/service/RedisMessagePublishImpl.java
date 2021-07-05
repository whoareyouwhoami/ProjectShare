package com.project.share.service;

import com.project.share.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RedisMessagePublishImpl implements RedisMessagePublish {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void publishMessage(ChannelTopic topic, MessageChatDetail message, MessageStructure messageStructure) {
        /* SAVE CHAT IN REDIS */
        String key = "message:m:" + message.getMessageChat().getId();
        Timestamp sentTime = Timestamp.valueOf(message.getSent());
        // redisTemplate.boundZSetOps(key).add(message, sentTime.getTime());

        /* PUSH TO REDIS PUB/SUB */
        redisTemplate.convertAndSend(topic.getTopic(), messageStructure);
    }

    @Override
    public void publishProjectMessage(ChannelTopic topic, MessageProjectDetail message) {
        /* SAVE CHAT IN REDIS */
        String key = "message:p:" + message.getMessageProject().getId();
        Timestamp sentTime = Timestamp.valueOf(message.getSent());
        redisTemplate.boundZSetOps(key).add(message, sentTime.getTime());

        /* PUSH TO REDIS PUB/SUB */
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
