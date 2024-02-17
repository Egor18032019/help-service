package com.example.subscriber;

import com.example.event.MessageQueue;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public class SubscriberInner implements Runnable {
    SubscriberWithAnnotation subscriberWithAnnotation;
    MessageQueue messageQueue;
    public SubscriberInner(SubscriberWithAnnotation subscriberWithAnnotation, MessageQueue messageQueue) {
        this.subscriberWithAnnotation = subscriberWithAnnotation;
        this.messageQueue = messageQueue;
    }


    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String message = messageQueue.poll();
//            System.out.println("SubscriberInner worked");
            if (message != null) {
                System.out.println("Вытащил message = " + message);
                subscriberWithAnnotation.save(message);
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
