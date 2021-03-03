package com.project.share.service;

import com.project.share.model.ChatMessageDetail;
import com.project.share.model.Project;

import java.util.List;

public interface MessageService {

    List<ChatMessageDetail> getAllMessages(int userId);

    // Get Room Number
    String getMessageId(Project project, int user) throws Exception;

    ChatMessageDetail getMessageDetail(long roomNumber);

    // Get ChatMessageDetail ID
    int getChatId(long roomNumber, int projectId);

}
