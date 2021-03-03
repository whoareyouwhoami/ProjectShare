package com.project.share.controller;

import com.project.share.model.ChatMessage;
import com.project.share.model.ChatMessageDetail;
import com.project.share.model.Project;
import com.project.share.model.User;
import com.project.share.service.*;

import org.springframework.beans.factory.annotation.Autowired;
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
    private KafkaService kafkaService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;

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
        for(ChatMessageDetail detail: messageDetailList) {
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
        if(messageDetail == null) {
            // Set reason -> message doesn't exist
            mv.setViewName("error/denied");
            return mv;
        }

        int userID = userService.getUserByEmail(principal.getName()).getId();
        if(!(userID == messageDetail.getUserOne() || userID == messageDetail.getUserTwo())) {
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

        // Retrieve any old messages from DB
        //

        // Retrieve any recent messages from Redis
        String key = "message:" + messageID;
        Set<ChatMessage> recentMessageList = redisService.getMessageList(key);
        if(!recentMessageList.isEmpty()) {
            mv.addObject("messages", recentMessageList);
        } else {
            System.out.println("Empty!");
        }

        return mv;
    }

    @GetMapping("/messages/p/{pid}")
    public String accessFromProjectDetail(@PathVariable("pid") int pid, Principal principal) {
        Project project = projectService.getProject(pid);
        int userId = userService.getUserByEmail(principal.getName()).getId();

        // Find message ID -> Creates message ID if it is not present in DB
        String messageID;
        try {
            messageID = messageService.getMessageId(project, userId);
        } catch (Exception e) {
            // If user tries to access user's project, redirect to project view
            return "redirect:/project/view/" + pid;
        }
        return "redirect:/messages/" + messageID;
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

        ChatMessage m = new ChatMessage(chatId, principal.getName(), message.getToUser(), message.getContent(), LocalDateTime.now(), message.getProjectId(), message.getRoomNumber());
        kafkaService.send("topicMessage", m);
    }
}
