package com.project.share.service;

import com.project.share.model.MessageChat;
import com.project.share.model.MessageDetail;
import com.project.share.model.Project;
import com.project.share.model.User;

public interface MessageChatService {
    MessageChat getMessageById(int messageId);

    MessageChat getMessageChat(Project project, User user);

    MessageChat addMessage(Project project, User user);

    void saveMessageDetail(MessageDetail detail);

    boolean checkMessageUserExistByProject(Project project, User user);

    /* MIGHT NOT BE NECESSARY */
    boolean checkMessageuserExistByMessageId(int messageId, User accessUser);
}
