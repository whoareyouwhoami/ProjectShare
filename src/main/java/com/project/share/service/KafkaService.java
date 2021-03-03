package com.project.share.service;

import com.project.share.model.ChatMessage;

public interface KafkaService {
    void send(String topic, Object object);
}
