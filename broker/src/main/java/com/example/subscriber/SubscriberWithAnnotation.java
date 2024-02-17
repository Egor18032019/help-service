package com.example.subscriber;

import com.example.annotation.Subscriber;
import com.example.store.GoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Subscriber()
@Component
@AllArgsConstructor
public class SubscriberWithAnnotation {

    GoodRepository goodRepository;

    public void save(String message) {

        System.out.println("Сохранил message = " + message);
        goodRepository.add(message + this.getClass().getPackage());
    }
}