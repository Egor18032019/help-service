package com.example.configuration;


import com.example.schemas.MessageRequest;
import com.example.utils.KafkaConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ProducerConfiguration {
    @Autowired
    Environment env;

    @Bean
    public ProducerFactory<String, MessageRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public KafkaTemplate<String, MessageRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        String KAFKA_BROKER = env.getProperty("KAFKA_BROKER");
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        configurations.put(ProducerConfig.RETRIES_CONFIG, 2);
        configurations.put(ProducerConfig.ACKS_CONFIG, KafkaConstants.ASK);
        configurations.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configurations.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-");

        return configurations;
    }

}