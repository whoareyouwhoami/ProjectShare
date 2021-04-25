package com.project.share.service;

import com.project.share.model.ChatMessage;
import com.project.share.model.Project;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    Set<ChatMessage> getMessageList(String key);

    void saveMessage(ChatMessage message);

    void saveProject(Project project);

    List<Map<Object, Object>> searchProject(String q);
}
