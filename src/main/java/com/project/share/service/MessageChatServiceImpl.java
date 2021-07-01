package com.project.share.service;

import com.project.share.dao.MessageChatDao;
import com.project.share.dao.MessageDetailDao_TEMPO;
import com.project.share.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageChatServiceImpl implements MessageChatService {

    @Autowired
    private MessageChatDao messageChatDao;

    @Autowired
    private MessageDetailDao_TEMPO messageDetailDao;

    @Override
    public MessageChat getMessageById(int messageId) {
        return messageChatDao.findById(messageId).orElse(null);
    }

    @Override
    public MessageChat getMessageChat(Project project, User user) {
        return messageChatDao.findByProjectAndUser(project, user);
    }

    @Override
    public MessageChat addMessage(Project project, User user) {
        MessageChat messageChat = new MessageChat();
        messageChat.setUser(user);
        messageChat.setProject(project);
        messageChat.setName("Let's talk about the project!");
        return messageChatDao.save(messageChat);
    }

    @Override
    public void saveMessageDetail(MessageDetail detail) {
        messageDetailDao.save(detail);
    }

    @Override
    public boolean checkMessageUserExist(Project project, User user) {
        MessageChat result = messageChatDao.findByProjectAndUser(project, user);
        return result != null;
    }
}
