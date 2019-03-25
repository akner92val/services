package com.mlavrenko.api.notification;

import com.mlavrenko.api.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

@Component
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
public class KafkaEventProducer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KafkaTemplate<String, Employee> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Employee> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    void sendMessage(String topic, Employee message, Optional<ListenableFutureCallback<SendResult<String, Employee>>> callback) {
        log.info("Sending message to topic {}, message:\n{}", topic, message);
        ListenableFuture<SendResult<String, Employee>> future = kafkaTemplate.send(topic, message);
        callback.ifPresent(future::addCallback);
    }
}
