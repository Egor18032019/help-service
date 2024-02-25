package com.example.consumer;

import com.example.schemas.MessageRequest;
import com.example.store.GoodRepository;
import com.example.utils.KafkaConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageListener {

    GoodRepository goodRepository;

    public MessageListener(  GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(MessageRequest message) {
        System.out.println("Consumer сработал !!");
        goodRepository.add(message.getPhrase());
    }
}