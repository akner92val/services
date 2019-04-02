package com.mlavrenko.api.config.kafka;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * Configuration for kafka messaging.
 */
@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${notification.kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${spring.application.name:event-service}")
    private String appName;

    @Bean
    Map<String, Object> kafkaConsumerConfigs() {
        return KafkaCommonProps.consumerProps(kafkaBootstrapServers, appName);
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
