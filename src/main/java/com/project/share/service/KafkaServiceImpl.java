package com.project.share.service;

import com.project.share.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String topic, Object object) {
        try {
            kafkaTemplate.send(topic, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
