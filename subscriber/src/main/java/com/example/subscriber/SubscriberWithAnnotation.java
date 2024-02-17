package com.example.subscriber;

import com.example.annotation.Subscriber;
import com.example.event.MessageQueue;
import com.example.store.GoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Subscriber(url="/support")
@Component
@AllArgsConstructor
public class SubscriberWithAnnotation {
    MessageQueue messageQueue;
    GoodRepository goodRepository;

    public void save(String message) {
        messageQueue.publish(message);
        System.out.println("Сохранил message = " + message);
        goodRepository.add(message + this.getClass().getPackage());
    }
}