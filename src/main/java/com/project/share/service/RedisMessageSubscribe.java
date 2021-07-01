package com.project.share.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.share.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscribe implements MessageListener {
    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    public RedisMessageSubscribe(ObjectMapper objectMapper, RedisTemplate<String, ChatMessage> redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessage chatMessage = objectMapper.readValue(body, ChatMessage.class);

            messageTemplate.convertAndSendToUser(chatMessage.getToUser(), "/secured/user/queue/specific-user", chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


