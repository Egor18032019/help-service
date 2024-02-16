package com.example.subscriber;

import com.example.controllers.MessageQueue;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber implements Runnable {
    MessageQueue messageQueue;

    public Subscriber(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String message = messageQueue.poll();
            if (message != null) {
                System.out.println("Вытащил message = " + message);
            } else {
                try {
                    Thread.sleep(1111);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Subscriber interrupted");
                    break;
                }
            }

        }
    }
}
