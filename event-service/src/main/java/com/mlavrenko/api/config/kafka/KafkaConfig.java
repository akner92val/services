package com.mlavrenko.api.config.kafka;

import com.mlavrenko.api.domain.EmployeeEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

/**
 * Configuration for kafka messaging.
 */
@Configuration
@EnableKafka
@ConditionalOnProperty(value = "notification.enabled", havingValue = "true")
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmployeeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties();
        ConsumerFactory<String, EmployeeEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(configs);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
