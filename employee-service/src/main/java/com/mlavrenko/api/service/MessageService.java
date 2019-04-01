package com.mlavrenko.api.service;

import com.mlavrenko.api.notification.KafkaEventProducer;
import com.mlavrenko.api.notification.Message;
import java.util.Optional;

public class MessageService {
    private final KafkaEventProducer kafkaEventProducer;

    public MessageService(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
    }

    public void sendMessage(Message message) {
        kafkaEventProducer.sendMessage("topic", message, Optional.empty());
    }
}
