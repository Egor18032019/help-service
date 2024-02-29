package com.example.kafka;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;

public class KafkaBase {
    private static final Logger log = LoggerFactory.getLogger(KafkaBase.class);

    private static final KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @Getter
    private static String bootstrapServers;

    public static void start(Collection<NewTopic> topics)
            throws ExecutionException, InterruptedException, TimeoutException {
        kafka.start();
        bootstrapServers = kafka.getBootstrapServers();

        log.info("topics creation...");
        try (var admin = AdminClient.create(Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers))) {
            var result = admin.createTopics(topics);

            for (var topicResult : result.values().values()) {
                System.out.println(topicResult);
                topicResult.get(10, TimeUnit.SECONDS);
            }
        }
        log.info("topics created");
    }

}
