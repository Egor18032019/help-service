package com.example.service;

import com.example.schemas.MessageRequest;
import com.example.store.GoodRepository;
import com.example.utils.KafkaConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaService {
    Environment env;
    GoodRepository goodRepository;
    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;

    public KafkaService(Environment env, GoodRepository goodRepository, KafkaTemplate<String, MessageRequest> kafkaTemplate) {
        this.env = env;
        this.goodRepository = goodRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(MessageRequest request) {
        boolean kafkaNeed = Boolean.parseBoolean(env.getProperty("KAFKA_NEED"));

        System.out.println("kafkaNeed " + kafkaNeed);
        if (kafkaNeed) {
            if (kafkaTemplate != null) {
                CompletableFuture<SendResult<String, MessageRequest>> future = kafkaTemplate.send
                        (KafkaConstants.KAFKA_TOPIC, String.valueOf(request.getPhrase().hashCode()), request);
                try {
//            future.whenComplete((result, ex) -> { не блокинг
                    future.get(10, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Сообщение не прошло!");
                }
            }
        } else {
            goodRepository.add(request.getPhrase());
        }
    }
}

