package com.mlavrenko.api.service;

import com.mlavrenko.api.notification.EmployeeEvent;
import com.mlavrenko.api.notification.KafkaEventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final KafkaEventProducer kafkaEventProducer;
    @Value("${notification.kafka.topicname.employee_event}")
    private String topic;

    public MessageService(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
    }

    public void sendMessage(EmployeeEvent employeeEvent) {
        kafkaEventProducer.sendMessage(topic, employeeEvent);
    }
}
