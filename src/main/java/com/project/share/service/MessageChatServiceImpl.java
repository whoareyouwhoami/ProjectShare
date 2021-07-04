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
        return messageChatDao.save(
                new MessageChat(project, user, "Let's talk about the project!")
        );
    }

    @Override
    public void saveMessageDetail(MessageDetail detail) {
        messageDetailDao.save(detail);
    }

    @Override
    public boolean checkMessageUserExistByProject(Project project, User user) {
        MessageChat result = messageChatDao.findByProjectAndUser(project, user);
        return result != null;
    }

    @Override
    public boolean checkMessageuserExistByMessageId(int messageId, User accessUser) {
        MessageChat result = messageChatDao.findById(messageId).orElse(null);
        if(result != null) {
            if(result.getUser().getId() == accessUser.getId() || result.getProject().getOwner().getId() == accessUser.getId()) {
                return true;
            }
        }
        return false;
    }
}
