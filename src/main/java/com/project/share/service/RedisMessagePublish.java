package com.project.share.service;

import com.project.share.model.*;
import org.springframework.data.redis.listener.ChannelTopic;

public interface RedisMessagePublish {

    void publishMessage(ChannelTopic topic, MessageDetail message, MessageStructure messageStructure);

    void publishProjectMessage(ChannelTopic topic, MessageProjectDetail message);

}
