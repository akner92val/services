package com.mlavrenko.api.notification;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
public class KafkaEventProducer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Message message, Optional<ListenableFutureCallback<SendResult<String, Message>>> callback) {
        log.info("Sending message to topic {}, message:\n{}", topic, message);
        ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send(topic, message);
        callback.ifPresent(future::addCallback);
    }
}
