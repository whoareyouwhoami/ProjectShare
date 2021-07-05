package com.project.share.service;

import com.project.share.model.*;
import org.springframework.data.redis.listener.ChannelTopic;

public interface RedisMessagePublish {

    void publishMessage(ChannelTopic topic, MessageChatDetail message, MessageStructure messageStructure);

    void publishProjectMessage(ChannelTopic topic, MessageProjectDetail message);

}
