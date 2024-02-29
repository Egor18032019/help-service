package com.example.configuration;

import com.example.consumer.MessageListener;
import com.example.store.GoodRepository;
import com.example.utils.KafkaConstants;
import com.example.schemas.MessageRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfiguration {
    @Autowired
    Environment env;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, MessageRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(MessageRequest.class));
    }

    @Bean
    public MessageListener messageConsumer(GoodRepository goodRepository) {
        boolean kafkaNeed = Boolean.parseBoolean(env.getProperty("KAFKA_NEED"));

        if (kafkaNeed) {
            return new MessageListener(goodRepository);
        } else {
            return null;
        }
    }

    @Bean
    public Map<String, Object> consumerConfigurations() {
        String KAFKA_BROKER = env.getProperty("KAFKA_BROKER");
        Map<String, Object> configurations = new HashMap<>();
        //устанавливаем адрес сервера, на котором работает Kafka.
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        //        для установки идентификатора группы потребителей Kafka.
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.ANOTHER_GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstants.OFFSET_EARLIEST);
        configurations.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        // «earliest», чтобы получить все значения в очереди с самого начала.
        // «latest», чтобы получить только самое последнее значение.
        return configurations;
    }
}