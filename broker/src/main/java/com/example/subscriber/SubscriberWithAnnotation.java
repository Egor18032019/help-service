package com.example.subscriber;

import org.example.annotation.Subscriber;
import com.example.store.GoodRepository;
import lombok.AllArgsConstructor;
import org.example.subscriber.SubscriberInterface;
import org.springframework.stereotype.Component;

@Subscriber()
@Component
@AllArgsConstructor
public class SubscriberWithAnnotation implements SubscriberInterface {

    GoodRepository goodRepository;

    public void save(String message) {

        System.out.println("Сохранил message = " + message);
        goodRepository.add(message + this.getClass().getPackage());
    }
}