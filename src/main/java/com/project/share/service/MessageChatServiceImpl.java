package com.project.share.service;

import com.project.share.dao.MessageChatDao;
import com.project.share.dao.MessageChatDetailDao;
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
    private MessageChatDetailDao messageChatDetailDao;

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
        /* SAVE PRIVATE CHAT ROOM */
        MessageChat messageChat = messageChatDao.save(
                new MessageChat(project, user, "Let's talk about the project!")
        );

        /* SAVE USERS FOR CHAT MESSAGE */
//        messageChatUserDao.save(new MessageChatUser(user, messageChat));
//        messageChatUserDao.save(new MessageChatUser(project.getAuthor(), messageChat));
        return messageChat;
    }

    @Override
    public void saveMessageDetail(MessageChatDetail detail) {
        messageChatDetailDao.save(detail);
    }

    @Override
    public boolean checkMessageUserExist(Project project, User user) {
        MessageChat result = messageChatDao.findByProjectAndUser(project, user);
        return result != null;
    }

    @Override
    public boolean checkMessageUserExistByMessageId(int messageId, User accessUser) {
        MessageChat result = messageChatDao.findById(messageId).orElse(null);
//        if(result != null) {
//            if(result.getUser().getId() == accessUser.getId() || result.getProject().getAuthor().getId() == accessUser.getId()) {
//                return true;
//            }
//        }
        return false;
    }
}
