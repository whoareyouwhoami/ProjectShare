package com.project.share.service;

import com.project.share.model.MessageChat;
import com.project.share.model.MessageChatDetail;
import com.project.share.model.Project;
import com.project.share.model.User;

public interface MessageChatService {
    MessageChat getMessageById(int messageId);

    MessageChat getMessageChat(Project project, User user);

    MessageChat addMessage(Project project, User user);

    void saveMessageDetail(MessageChatDetail detail);

    boolean checkMessageUserExist(Project project, User user);

    /* MIGHT NOT BE NECESSARY */
    boolean checkMessageUserExistByMessageId(int messageId, User accessUser);
}
