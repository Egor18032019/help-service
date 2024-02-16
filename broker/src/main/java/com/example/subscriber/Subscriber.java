package com.example.subscriber;

import com.example.event.MessageQueue;
import com.example.store.GoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Subscriber implements Runnable {
    MessageQueue messageQueue;
    GoodRepository goodRepository;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String message = messageQueue.poll();
            if (message != null) {
                System.out.println("Вытащил message = " + message);
                goodRepository.add(message + this.getClass().getPackage());
            } else {
                try {
                    Thread.sleep(111111);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Subscriber interrupted");
                    break;
                }
            }

        }
    }
}
