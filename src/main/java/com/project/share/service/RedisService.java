package com.project.share.service;

import com.project.share.model.ChatMessage;

import java.util.Set;

public interface RedisService {
    Set<ChatMessage> getMessageList(String key);

    void saveMessage(ChatMessage message);
}
