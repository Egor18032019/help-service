package com.example.subscriber;

import com.example.network.HttpURLConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;


@AllArgsConstructor
public class SubscriberComon implements Runnable {
    HttpURLConnectionService httpURLConnectionService;

    SubscriberWithAnnotation subscriberWithAnnotation;
    private final String url;

    public SubscriberComon(SubscriberWithAnnotation subscriberWithAnnotation, String url) {
        this.subscriberWithAnnotation = subscriberWithAnnotation;
        this.httpURLConnectionService = new HttpURLConnectionService();
        this.url = url;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {

        while (true) {
            String message=null;
            try {
                message = httpURLConnectionService.sendHttpGETRequest(url);
            } catch (IOException e) {
                System.out.println("Пустой ответ.");
                sleep(1111);
            }
            if (message != null) {
                System.out.println("Вытащил message = " + message);
                subscriberWithAnnotation.save(message);
            } else {
                sleep(2222);
            }
        }
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Subscriber interrupted");
        }
    }
}
