package com.project.share.controller;

import com.project.share.model.*;
import com.project.share.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    @Autowired
    private MessageProjectService messageProjectService;

    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public MessageController(RedisMessageListenerContainer redisMessageListenerContainer) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    private final Map<String, ChannelTopic> channelMap = new HashMap<>();

    @GetMapping("/messages")
    public ModelAndView messageHome(Principal principal) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("message/messageInbox");

        // Get user information
        int userID = userService.getUserByEmail(principal.getName()).getId();

        // Retrieve user's chat list
        List<ChatMessageDetail> messageDetailList = messageService.getAllMessages(userID);
        Map<String, Map<Project, User>> information = new HashMap<>();

        // Add details
        for (ChatMessageDetail detail : messageDetailList) {
            int receiverID = (userID == detail.getUserOne()) ? detail.getUserTwo() : detail.getUserOne();
            User receiver = userService.getUserById(receiverID);

            Map<Project, User> map = new HashMap<>();
            map.put(projectService.getProject(detail.getProjectId()), receiver);
            information.put(Long.toString(detail.getRoomNumber()), map);
        }

        mv.addObject("messageList", information);
        return mv;
    }

    @GetMapping("/messages/{mid}")
    public ModelAndView messageDetails(@PathVariable("mid") String messageID, Principal principal) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("message/messageDetail");

        ChatMessageDetail messageDetail = messageService.getMessageDetail(Long.parseLong(messageID));
        if (messageDetail == null) {
            // Set reason -> message doesn't exist
            mv.setViewName("error/denied");
            return mv;
        }

        int userID = userService.getUserByEmail(principal.getName()).getId();
        if (!(userID == messageDetail.getUserOne() || userID == messageDetail.getUserTwo())) {
            // Set reason -> access denied
            mv.setViewName("error/denied");
            return mv;
        }

        // Get sender and Project ID
        int receiverID = (userID == messageDetail.getUserOne()) ? messageDetail.getUserTwo() : messageDetail.getUserOne();
        int projectID = messageDetail.getProjectId();
        User receiver = userService.getUserById(receiverID);

        mv.addObject("authorInfo", receiver);
        mv.addObject("projectInfo", projectID);
        mv.addObject("showMessageList", true);

        // Redis pub/sub
        String key = "message:" + messageID;
        ChannelTopic channel = new ChannelTopic(key);
        if (!channelMap.containsKey(key)) {
            redisMessageListenerContainer.addMessageListener(redisMessageSubscribe, channel);
            channelMap.put(key, channel);
        }

        // Retrieve any old messages from DB
        //

        // Retrieve any recent messages from Redis


        return mv;
    }

    @GetMapping("/messages/d/{pid}")
    public String redirectMessageChat(@PathVariable("pid") int projectId, Principal principal) {
        Project project = projectService.getProject(projectId);
        User currentUser = userService.getUserByEmail(principal.getName());

        /* PREVENT CURRENT USER TO ACCESS ITS OWN PROJECT'S MESSAGE */
        if(project.getOwner().getId() == currentUser.getId()) {
            return "redirect:/project/view/" + projectId;
        }

        /* RETRIEVE MESSAGE BETWEEN USER AND PROJECT AUTHOR */
        MessageChat messageChat = messageChatService.getMessageChat(project, currentUser);

        /* CREATES MESSAGE IF IT DOESN'T EXIST */
        if(messageChat == null) {
            messageChat = messageChatService.addMessage(project, currentUser);
        }
        return "redirect:/messages/m/" + messageChat.getId();
    }

    @GetMapping("/messages/m/{mid}")
    public ModelAndView accessPrivateMessage(@PathVariable("mid") int messageId, Principal principal) {
        ModelAndView mv = new ModelAndView();
        User currentUser = userService.getUserByEmail(principal.getName());
        MessageChat messageChat = messageChatService.getMessageById(messageId);

        /* CHECK IF MESSAGE EXIST AND IS OWNED BY THE USER OR PROJECT AUTHOR */
        if (messageChat == null || (messageChat.getUser().getId() != currentUser.getId() && messageChat.getProject().getOwner().getId() != currentUser.getId())) {
            mv.setViewName("redirect:/messages");
            return mv;
        }

        mv.addObject("showMessageList", true);
        mv.addObject("authorInfo", messageChat.getProject().getOwner());
        mv.addObject("projectInfo", messageChat.getProject().getId());
        mv.addObject("messageChat", messageChat);

        /* SUBSCRIBE TO REDIS */
        String key = "message:m:" + messageId;
        ChannelTopic channel = new ChannelTopic(key);
        if (!channelMap.containsKey(key)) {
            redisMessageListenerContainer.addMessageListener(redisMessageSubscribe, channel);
            channelMap.put(key, channel);
        }
        System.out.println("ChannelMap: " + channelMap);

        /* SHOW RECENT MESSAGES FROM REDIS */
        Set<Object> recentMessages = redisService.getRecentMessages(key);
        if (!recentMessages.isEmpty()) {
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
        ModelAndView mv = new ModelAndView();
        User currentUser = userService.getUserByEmail(principal.getName());
        MessageProject messageProject = messageProjectService.getMessageProjectById(messageId);

        /* CHECK IF GROUP MESSAGE EXIST AND CURRENT USER IS PART OF THE MESSAGE GROUP */
        if (messageProject == null || !messageProjectService.checkMessageProjectUserExist(currentUser, messageProject)) {
            mv.setViewName("redirect:/messages");
            return mv;
        }

        /* SUBSCRIBE TO REDIS */
        String key = "message:p:" + messageId;
        ChannelTopic channel = new ChannelTopic(key);
        if (!channelMap.containsKey(key)) {
            redisMessageListenerContainer.addMessageListener(redisMessageSubscribe, channel);
            channelMap.put(key, channel);
        }

        /* SHOW RECENT MESSAGES FROM REDIS */
        Set<Object> recentMessages = redisService.getRecentMessages(key);
        if (!recentMessages.isEmpty()) {
            mv.addObject("messages", recentMessages);
        }

        /* SHOW OLD MESSAGES FROM DB (TEMPORARY - TO BE CHANGED...) */
        Set<MessageProjectDetail> oldMessages = messageProject.getMessageProjectDetailSet();
        // ...
        // ...

        mv.setViewName("message/messageDetail");
        return mv;
    }

    @GetMapping("/messages/p/add/{mid}")
    private String addGroupUser(@PathVariable("mid") int messageId, Principal principal) {
        MessageChat messageChat = messageChatService.getMessageById(messageId);

        /* CHECK IF PROJECT AUTHOR ADDED A USER */
        if(messageChat == null || !messageChat.getProject().getOwner().getEmail().equals(principal.getName())) {
            return "redirect:/messages/m/" + messageId;
        }

        /* ADD USER TO THE GROUP */
        MessageProject messageProject = messageProjectService.getMessageProject(messageChat.getProject());
        messageProjectService.addMessageProjectUser(messageChat.getUser(), messageProject);
        System.out.println("ADDED USER TO A GROUP");
        return "/messages/m/" + messageId;
    }

    @MessageMapping("/secured/room")
    public void sendMessage(@Payload MessageStructure message, Principal principal, @Header("simpSessionId") String sessionId) {
        /* CHECK IF A TOPIC EXIST */
        String key = "message:" + message.getType() + ":" + message.getMessage();

        System.out.println("Key: " + key);
        System.out.println("ChannelMap: " + channelMap);
        if(!channelMap.containsKey(key)) {
            System.out.println("ERROR... KEY DOESN'T EXIST");
        }

        User currentUser = userService.getUserByEmail(principal.getName());
        Project project = projectService.getProject(message.getProject());
        if(project == null) {
            System.out.println("PROJECT DOESN'T EXIST");
            return;
        }

        /* PUBLISH TO CORRESPONDING MESSAGE TYPE */
        if(message.getType().equals("m")) {
            MessageChat messageChat = messageChatService.getMessageById(message.getMessage());
            MessageDetail messageDetail = new MessageDetail(messageChat, currentUser, message.getContent(), LocalDateTime.now());

            if(currentUser.getId() == project.getOwner().getId()) {
                message.setReceiver(messageChat.getUser().getEmail());
            } else {
                message.setReceiver(project.getOwner().getEmail());
            }

            redisMessagePublish.publishMessage(channelMap.get(key), messageDetail, message);
        } else if(message.getType().equals("p")) {
            MessageProject messageProject = messageProjectService.getMessageProjectById(message.getMessage());
            MessageProjectDetail messageProjectDetail = new MessageProjectDetail(messageProject, currentUser, message.getContent(), LocalDateTime.now());

            redisMessagePublish.publishProjectMessage(channelMap.get(key), messageProjectDetail);
        } else {
            System.out.println("ERROR...");
        }
    }
}
