package com.project.share.component;

import com.project.share.model.ChatMessage;
import com.project.share.model.Project;
import com.project.share.service.ProjectService;
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

    @Autowired
    private ProjectService projectService;

    // Message
    // @KafkaListener(topics = "topicMessage", groupId = "groupMessage", containerFactory = "listenerFactoryMessaging")
    // public void listenMessaging(ChatMessage message) {
    //     simpMessagingTemplate.convertAndSendToUser(message.getToUser(), "/secured/user/queue/specific-user", message);
    // }

    // Redis
    // @KafkaListener(topics = "topicMessage", groupId = "groupRedis", containerFactory = "listenerFactoryRedis")
    // public void listenRedis(ChatMessage message) {
    //     System.out.println("GOING TO SLEEP...");
    //     try {
    //         Thread.sleep(5000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    //     System.out.println("WAKE UP!");
    //     // redisService.saveMessage(message);
    // }

    @KafkaListener(topics = "topicRediSearch", groupId = "groupRedis", containerFactory = "listenerFactoryRediSearch")
    public void listenRediSearch(Project project) {
        redisService.saveProject(project);
    }

    // ES
    // @KafkaListener(topics = "topicES", groupId = "groupES", containerFactory = "listenerFactoryES")
    // public void listenES(Project project) {
    //     projectService.esSaveProject(project);
    // }
}
