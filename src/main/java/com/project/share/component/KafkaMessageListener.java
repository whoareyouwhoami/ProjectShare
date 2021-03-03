package com.project.share.component;

import com.project.share.model.ChatMessage;
import com.project.share.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisService redisService;

    // Message
    @KafkaListener(topics = "topicMessage", groupId = "groupMessage", containerFactory = "listerFactoryMessaging")
    public void listenMessaging(ChatMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getToUser(), "/secured/user/queue/specific-user", message);
    }

    // Redis
    @KafkaListener(topics = "topicMessage", groupId = "groupRedis", containerFactory = "listerFactoryRedis")
    public void listenRedis(ChatMessage message) {
        redisService.saveMessage(message);
    }
}
