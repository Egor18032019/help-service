package com.example.kafka;

import com.example.schemas.MessageRequest;
import com.example.utils.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class SendInKafkaTest {
    private final Duration timeout = Duration.ofMillis(2_000);

    @BeforeAll
    public static void init() throws ExecutionException, InterruptedException, TimeoutException {
        KafkaBase.start(List.of(new NewTopic(KafkaConstants.KAFKA_TOPIC, 1, (short) 1)));
    }


    @Test
    void isBackWork() {
        //  отправили одно сообщение
        putValuesToKafka();
        var myConsumer = new MyConsumer(KafkaBase.getBootstrapServers());
        ConsumerRecords<String, MessageRequest> records = myConsumer.getConsumer().poll(timeout);
        for (ConsumerRecord<String, MessageRequest> kafkaRecord : records) {
            try {
                var key = kafkaRecord.key();
                var value = kafkaRecord.value();
                System.out.println("value " + value + " " + key);
            } catch (Exception ex) {
                System.out.println("херня какая-то " + records.toString());
            }
        }
    }

    private void putValuesToKafka() {
        Map<String, Object> props = new HashMap<>();
        props.put(CLIENT_ID_CONFIG, "myKafkaTestProducer");
        props.put(BOOTSTRAP_SERVERS_CONFIG, KafkaBase.getBootstrapServers());

        props.put(LINGER_MS_CONFIG, 1);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(ProducerConfig.RETRIES_CONFIG, 2);
        props.put(ProducerConfig.ACKS_CONFIG, KafkaConstants.ASK);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-");
        ProducerFactory<String, MessageRequest> producerFactory = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, MessageRequest> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        MessageRequest request = new MessageRequest("idea");

        kafkaTemplate.executeInTransaction(kt -> {
            CompletableFuture<SendResult<String, MessageRequest>> future = kafkaTemplate.send
                    (KafkaConstants.KAFKA_TOPIC, String.valueOf(request.getPhrase().hashCode()), request);
//                        future.get(10, TimeUnit.SECONDS);
            future.whenComplete(((sendResult, throwable) -> {
                if (throwable != null) {
                    System.out.println("не ушло" + throwable);
                }
            }));
            return future;
        });

    }
}
