package com.project.share.controller;

import com.project.share.model.*;
import com.project.share.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisMessageSubscribe redisMessageSubscribe;

    @Autowired
    private RedisMessagePublish redisMessagePublish;

    @Autowired
    private MessageChatService messageChatService;

    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public MessageController(RedisMessageListenerContainer redisMessageListenerContainer) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    private final Map<String, ChannelTopic> channelMap = new HashMap<>();

    @GetMapping("/messages/m/{mid}")
    public ModelAndView accessPrivateMessage(@PathVariable("mid") int messageId, Principal principal) {
        ModelAndView mv = new ModelAndView();
        User currentUser = userService.getUserByEmail(principal.getName());
        MessageChat messageChat = messageChatService.getMessageById(messageId);

        /* CHECK IF MESSAGE EXIST AND IS OWNED BY THE USER */
        if(messageChat == null || messageChat.getUser().getId() != currentUser.getId() || messageChat.getProject().getOwner().getId() != currentUser.getId()) {
            mv.setViewName("redirect:/messages");
            return mv;
        }

        /* SUBSCRIBE TO REDIS */
        String key = "message:m:" + messageId;
        ChannelTopic channel = new ChannelTopic(key);
        if(!channelMap.containsKey(key)) {
            redisMessageListenerContainer.addMessageListener(redisMessageSubscribe, channel);
            channelMap.put(key, channel);
        }

        /* SHOW RECENT MESSAGES FROM REDIS */
        Set<MessageDetail> recentMessages = redisService.getRecentMessages(key);
        if(!recentMessages.isEmpty()) {
            mv.addObject("messages", recentMessages);
        }

        /* SHOW OLD MESSAGES FROM DB (TEMPORARY - TO BE CHANGED...) */
        Set<MessageDetail> oldMessages = messageChat.getMessageDetailSet();
        // ...
        // ...

        mv.setViewName("message/messageDetail");
        return mv;
    }

    @GetMapping("/messages/p/{mid}")
    public ModelAndView accessGroupMessage(@PathVariable("mid") int messageId, Principal principal) {
        return new ModelAndView();
    }



    @MessageMapping("/secured/room")
    public void sendMessage(@Payload ChatMessage message, Principal principal, @Header("simpSessionId") String sessionId) {
        long roomNumber = message.getRoomNumber();

        if(roomNumber == 0) {
            System.out.println("ERROR");
            return;
        }

        int chatId = messageService.getChatId(roomNumber, message.getProjectId());
        if(chatId == -1) {
            System.out.println("ERROR");
            return;
        }

        ChatMessage chatMessage = new ChatMessage(chatId, principal.getName(), message.getToUser(), message.getContent(), LocalDateTime.now(), message.getProjectId(), message.getRoomNumber());
        String key = "message:" + roomNumber;
        if(channelMap.containsKey(key)) {
            System.out.println("Channel: " + channelMap.get(key).getTopic());
            redisMessagePublish.publish(channelMap.get(key), chatMessage);
        }
    }
}
