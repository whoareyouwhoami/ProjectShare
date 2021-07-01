package com.project.share.service;

import com.project.share.model.ChatMessage;
import org.springframework.data.redis.listener.ChannelTopic;

public interface RedisMessagePublish {

    void publish(ChannelTopic topic, ChatMessage chatMessage);
}
