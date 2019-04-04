package com.mlavrenko.api.config;

import com.mlavrenko.api.notification.KafkaEventProducer;
import com.mlavrenko.api.service.MessageService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @MockBean
    private KafkaEventProducer kafkaEventProducer;

    @Bean
    public MessageService getMessageService() {
        return new MessageService(kafkaEventProducer);
    }
}
