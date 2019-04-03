package com.mlavrenko.api.config.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public interface KafkaCommonProps {
    static Map<String, Object> consumerProps(String brokers, String appName) {
        final Map<String, Object> props = new HashMap<>();
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        /*props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.CLIENT_ID_CONFIG, appName);
        //props.put(ConsumerConfig.GROUP_ID_CONFIG, appName);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");*/
        return props;
    }
}
