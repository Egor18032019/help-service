package com.example.service;

import com.example.schemas.MessageRequest;
import com.example.store.GoodRepository;
import com.example.utils.KafkaConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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

    public void produce(MessageRequest request) {
        boolean kafkaNeed = Boolean.parseBoolean(env.getProperty("KAFKA_NEED"));

        if (kafkaNeed) {
            boolean phraseContainsInStorage = goodRepository.isPhraseContainsInStorage(request.getPhrase());
            if (kafkaTemplate != null && !phraseContainsInStorage) {

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

            } else {
                goodRepository.add(request.getPhrase());
            }
        }
    }
}

