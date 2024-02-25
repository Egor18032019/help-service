package com.example.kafka;

import com.example.schemas.MessageRequest;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class MyConsumer {
    private final Random random = new Random();
    private final KafkaConsumer<String, MessageRequest> kafkaConsumer;

    public static final String TOPIC_NAME = "MyTopic";
    public static final String GROUP_ID_CONFIG_NAME = "myKafkaConsumerGroup";
    public static final int MAX_POLL_INTERVAL_MS = 300;

    public MyConsumer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(GROUP_ID_CONFIG, GROUP_ID_CONFIG_NAME);
        props.put(GROUP_INSTANCE_ID_CONFIG, makeGroupInstanceIdConfig());
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(MAX_POLL_RECORDS_CONFIG, 3);
        props.put(MAX_POLL_INTERVAL_MS_CONFIG, MAX_POLL_INTERVAL_MS);

        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
    }

    public KafkaConsumer<String, MessageRequest> getConsumer() {
        return kafkaConsumer;
    }

    public String makeGroupInstanceIdConfig() {
        try {
            var hostName = InetAddress.getLocalHost().getHostName();
            return String.join(
                    "-",
                    GROUP_ID_CONFIG_NAME,
                    hostName,
                    String.valueOf(random.nextInt(100_999_999)));
        } catch (Exception ex) {
            throw new RuntimeException("can't make GroupInstanceIdConfig", ex);
        }
    }
}
