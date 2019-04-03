package com.mlavrenko.api.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
class EventListener {
    private Logger log = LoggerFactory.getLogger(getClass());
    private EventConsumerService consumerService;

    EventListener(EventConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @KafkaListener(topics = {"${notification.kafka.topicname.employee_event}"})
    void consumeMessage(String message) {
        log.info("Received message = '{}'", message);
        processJson(message);
    }

    private void processJson(String message) {
        try {
            consumerService.process(message);
        } catch (Exception e) {
            log.error("Message processing failed, message='{}'", message, e);
        }
    }
}
