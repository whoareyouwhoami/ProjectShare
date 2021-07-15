package com.project.share.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.share.model.MessageStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscribe implements MessageListener {
    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    public RedisMessageSubscribe(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            MessageStructure messageStructure = objectMapper.readValue(body, MessageStructure.class);
            if(messageStructure.getType().equals("m")) {
                messageTemplate.convertAndSendToUser(messageStructure.getReceiver(), "/secured/user/queue/specific-user", messageStructure);
            } else if(messageStructure.getType().equals("p")) {
                System.out.println("Sending Group Message");
                messageTemplate.convertAndSend("/secured/user/queue/group/p." + messageStructure.getMessage(), messageStructure);
            } else {
                System.out.println("ERROR...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


